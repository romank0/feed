package info.romank0.feed;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.feed.AbstractAtomFeedView;

import com.rometools.rome.feed.atom.Content;
import com.rometools.rome.feed.atom.Entry;
import com.rometools.rome.feed.atom.Feed;
import com.rometools.rome.feed.atom.Link;


public class ContentAtomView extends AbstractAtomFeedView {
    @Override
    protected void buildFeedMetadata(Map<String, Object> model, Feed feed, 
            HttpServletRequest request) {
        feed.setId("id1234");
        feed.setTitle("romank0.info");
        List<Link> links = new ArrayList<>();
        Link link = new Link();
        link.setHref("http://www.romank0.info");
        links.add(link);
        feed.setAlternateLinks(links);
	}

	@Override
	protected List<Entry> buildFeedEntries(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<Entry> entries = new ArrayList<Entry>();
		Object ob = model.get("feeds");
		if (ob instanceof List) {
			for (int i = 0; i < ((List<?>) ob).size(); i++) {
				Object feedObj = ((List<?>) ob).get(i);
				MyFeed myFeed = (MyFeed) feedObj;
				Entry entry = new Entry();
				entry.setId(myFeed.getFeedId());
				entry.setPublished(myFeed.getPubDate());
				entry.setTitle(myFeed.getTitle());
				List<Link> links = new ArrayList<>();
				Link link = new Link();
				link.setHref(myFeed.getLink());
				links.add(link);
				entry.setAlternateLinks(links);
				Content content = new Content();
				content.setValue(myFeed.getDescription());
				entry.setSummary(content);
				Content audioContent = new Content();
				audioContent.setType("audio/mpeg");
				audioContent.setValue("http://feedproxy.google.com/~r/RealPolish/~5/wO_bFGTTOdY/RP287-Rasid.mp3");
				entry.setContents(Arrays.asList(audioContent));;
				entries.add(entry);
			}
		}
		return entries;
	}
} 
