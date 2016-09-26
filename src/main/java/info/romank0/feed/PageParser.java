package info.romank0.feed;

import static java.util.Arrays.asList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PageParser {
	
	private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";

	public static interface Visitor {
		void visit(Document document);
	}
	
	public List<String> parse(String pageUrl, Predicate<String> filter, Visitor visitor) {
		try
        {
            Connection connection = Jsoup.connect(pageUrl).userAgent(USER_AGENT);
            Document htmlDocument;
            try {
            	htmlDocument = connection.get();
            } catch (HttpStatusException e) {
            	if (e.getStatusCode() == 404) {
            		return new ArrayList<>();
            	} else {
            		throw e;
            	}
            }

            System.out.println("Received web page at " + pageUrl);
            
            visitor.visit(htmlDocument);

            Elements linksOnPage = htmlDocument.select("a[href]");
            System.out.println("Found (" + linksOnPage.size() + ") links");
            ArrayList<String> links = new ArrayList<>();
            for(Element link : linksOnPage)
            {
				String url = link.absUrl("href");
				if (filter.test(url)) {
					links.add(url);
				}
            }
            return links;
        }
        catch(IOException ioe)
        {
            throw new RuntimeException("error during page parse", ioe);
        }
	}

	public Collection<String> parseRecursive(String pageUrl, Predicate<String> filter, Visitor visitor) {
		Set<String> visited = new HashSet<>();
		Set<String> newPages = new HashSet<>(asList(pageUrl));
		do {
			visited.addAll(newPages);
			Set<String> unprocessed = newPages;
			newPages = new HashSet<>();
			for (String url : unprocessed)
				newPages.addAll(parse(url, filter, visitor));
			newPages.removeAll(visited);
		} while (!newPages.isEmpty());
		return visited;
	}

}
