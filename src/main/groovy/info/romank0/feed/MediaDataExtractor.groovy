package info.romank0.feed;


import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MediaDataExtractor {
	ObjectMapper mapper = new ObjectMapper();

	public MediaData extract(String mediaDataString) {
		
		def map = mapper.readValue(mediaDataString.getBytes(), Map.class);
		def mediaData = new MediaData(
			url:map['file'],
			id:map['id'],
			uid:map['uid'],
			description: URLDecoder.decode(map['desc'], "UTF8")
			)
		return mediaData;
	}
}
