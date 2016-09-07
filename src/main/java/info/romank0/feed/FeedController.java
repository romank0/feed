package info.romank0.feed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class FeedController {
	@Autowired
	private MyFeed myFeed;
	@RequestMapping(value="/feed.*", method=RequestMethod.GET)
	public ModelAndView getContent() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("feeds", myFeed.createFeed());
		return mav;
	}
}