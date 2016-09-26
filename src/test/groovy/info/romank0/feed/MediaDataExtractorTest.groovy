package info.romank0.feed;

import static org.junit.Assert.*;

import org.hamcrest.Matchers;
import org.junit.Test;

class MediaDataExtractorTest {

	def mediaDataString = '{"id":1671954,"file":"//static.prsa.pl/7f275824-dfd1-4fd4-8759-1ec6d6f830d6.mp3","provider":"audio","uid":"7f275824-dfd1-4fd4-8759-1ec6d6f830d6","length":1257,"autostart":true,"link":"/130/2412/Artykul/1664327,Miroslaw-Oczkos-o-probie-odwolania-marszalka-Kuchcinskiego","title":"00c7bd41.mp3","desc":"06.09.16%20Miros%C5%82aw%20Oczko%C5%9B%3A%20%E2%80%9ENajbardziej%20kuriozalnym%20elementem%20debaty%20by%C5%82o%20to%2C%20%C5%BCe%20marsza%C5%82ek%20wyci%C4%85%C5%82%20zadawanie%20pyta%C5%84%E2%80%9D.","advert":0,"type":"muzyka"}'
	
	MediaDataExtractor sut = new MediaDataExtractor();
	
	@Test
	public void extractsMediaData() {
		MediaData mediaData = sut.extract(mediaDataString)

		assert mediaData != null		
		assert mediaData.url == "//static.prsa.pl/7f275824-dfd1-4fd4-8759-1ec6d6f830d6.mp3"
		assert mediaData.description == "06.09.16 Mirosław Oczkoś: „Najbardziej kuriozalnym elementem debaty było to, że marszałek wyciął zadawanie pytań”."
		assert mediaData.id == 1671954
		assert mediaData.uid == '7f275824-dfd1-4fd4-8759-1ec6d6f830d6'
	}

}
