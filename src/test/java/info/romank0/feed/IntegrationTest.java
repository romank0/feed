package info.romank0.feed;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;
import info.romank0.feed.PageParser.Visitor;
import info.romank0.feed.config.Application;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

import org.hamcrest.Matchers;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment=DEFINED_PORT)
public class IntegrationTest {
	private static final String ROOT_URL = "http://localhost:8080/130,PolskieRadio24.1";
	private static final Predicate<String> RADIO24_PAGE = url -> url.startsWith("http://localhost:8080/130,PolskieRadio24") || url.matches("http://localhost:8080/130/.*/Artykul/.*");
	private PageParser sut = new PageParser();

	@SuppressWarnings("unchecked")
	@Test
	public void parserExtractsLinks() {
		List<String> urls = sut.parse(ROOT_URL, RADIO24_PAGE, document -> {});
		assertThat(urls, hasItems(containsString("Polityka"), containsString("Sport")));
		assertThat(urls, Matchers.not(hasItems("http://localhost:8080/130/2412/Artykul/1664327,Miroslaw-Oczkos-o-probie-odwolania-marszalka-Kuchcinskiego")));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void crawlVisitPagesRecursively() {
		final Collection<String> recursiveUrls = new ArrayList<>();
		sut.parseRecursive(ROOT_URL, RADIO24_PAGE, document -> recursiveUrls.add(document.location()));
		assertThat(recursiveUrls, hasItems(containsString("Polityka"), containsString("Sport")));
		assertThat(recursiveUrls, hasItems("http://localhost:8080/130/2412/Artykul/1664327,Miroslaw-Oczkos-o-probie-odwolania-marszalka-Kuchcinskiego"));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void extractsMediaData() {
		final Collection<MediaData> medias = new ArrayList<>();
		final MediaDataExtractor extractor = new MediaDataExtractor();
		sut.parseRecursive("http://localhost:8080/130/2412/Artykul/1664327,Miroslaw-Oczkos-o-probie-odwolania-marszalka-Kuchcinskiego", RADIO24_PAGE,
				document -> {
					Elements mediaDatas = document.select("span.play[data-media]");
					for(Element el:mediaDatas) {
						String mediaDataString = el.attr("data-media");
						medias.add(extractor.extract(mediaDataString));
					}
				});
		assertThat(medias, hasItems(Matchers.hasProperty("url", containsString("static.prsa.pl/7f275824-dfd1-4fd4-8759-1ec6d6f830d6.mp3"))));
	}
}
