package th.co.locus.jlo.integration.line.message;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.linecorp.bot.model.action.PostbackAction;
import com.linecorp.bot.model.action.URIAction;
import com.linecorp.bot.model.message.FlexMessage;
import com.linecorp.bot.model.message.flex.component.Box;
import com.linecorp.bot.model.message.flex.component.Button;
import com.linecorp.bot.model.message.flex.component.FlexComponent;
import com.linecorp.bot.model.message.flex.component.Image;
import com.linecorp.bot.model.message.flex.component.Text;
import com.linecorp.bot.model.message.flex.container.Bubble;
import com.linecorp.bot.model.message.flex.container.Carousel;
import com.linecorp.bot.model.message.flex.unit.FlexFontSize;
import com.linecorp.bot.model.message.flex.unit.FlexGravity;
import com.linecorp.bot.model.message.flex.unit.FlexLayout;
import com.linecorp.bot.model.message.flex.unit.FlexMarginSize;

import th.co.locus.jlo.business.redemption.bean.RedemptionRewardModelBean;

public class CatalogFlexMessageBuilder {
	
	private static final String DEFAULT_PATH = "https://jlo.locus.co.th/JLOBE/api/reward/reward_image/";

	public static FlexMessage build(Long memberId, List<RedemptionRewardModelBean> rewardList) {
		try {
			List<Bubble> bubbles = new ArrayList<Bubble>();
			for (RedemptionRewardModelBean reward : rewardList) {
				bubbles.add(createBubble(memberId, reward));
			}
			final Carousel carousel = Carousel.builder().contents(bubbles).build();
			return new FlexMessage("Catalogue", carousel);
		} catch (Exception e) {
			e.printStackTrace();
			return new FlexMessage("Catalogue", null);
		}
	}

	private static Bubble createBubble(Long memberId, RedemptionRewardModelBean reward)
			throws URISyntaxException {
		final Image heroBlock = createHeroBlock(DEFAULT_PATH + reward.getProductImgPath());
		final Box bodyBlock = createBodyBlock(reward.getProductName(), reward.getPoint().toString(), false);
		final Box footerBlock = createFooterBlock(memberId, reward);
		return Bubble.builder().hero(heroBlock).body(bodyBlock).footer(footerBlock).build();
	}

	private static Bubble createSeeMoreBubble() throws URISyntaxException {
		return Bubble.builder().body(Box.builder().layout(FlexLayout.VERTICAL).spacing(FlexMarginSize.SM)
				.contents(Arrays.asList(Button.builder().flex(1).gravity(FlexGravity.CENTER)
						.action(new URIAction("See more", new URI("http://www.amazon.com"), null)).build()))
				.build()).build();
	}

	private static Image createHeroBlock(String imageURL) throws URISyntaxException {
		return Image.builder().size(Image.ImageSize.FULL_WIDTH).aspectRatio(Image.ImageAspectRatio.R20TO13)
				.aspectMode(Image.ImageAspectMode.Cover).url(new URI(imageURL)).build();
	}

	private static Box createBodyBlock(String title, String price, Boolean isOutOfStock) {
		final Text titleBlock = Text.builder().text(title).wrap(true).weight(Text.TextWeight.BOLD).size(FlexFontSize.XL)
				.build();
		final Box priceBlock = Box.builder().layout(FlexLayout.BASELINE)
				.contents(Arrays.asList(
						Text.builder().text(price + " point").wrap(true).weight(Text.TextWeight.REGULAR)
								.size(FlexFontSize.XL).flex(0).build()))
				.build();
		final Text outOfStock = Text.builder().text("Temporarily out of stock").wrap(true).size(FlexFontSize.XXS)
				.margin(FlexMarginSize.MD).color("#FF5551").build();

		FlexComponent[] flexComponents = { titleBlock, priceBlock };
		List<FlexComponent> listComponent = new ArrayList<>(Arrays.asList(flexComponents));
		if (isOutOfStock) {
			listComponent.add(outOfStock);
		}

		return Box.builder().layout(FlexLayout.VERTICAL).spacing(FlexMarginSize.SM).contents(listComponent).build();
	}

	private static Box createFooterBlock(Long memberId, RedemptionRewardModelBean reward) throws URISyntaxException {
		final Button addToCartEnableButton = Button.builder().style(Button.ButtonStyle.PRIMARY)
				.action(new PostbackAction("Redeem", "redeem:" + memberId + "," + reward.getProductCode() + "," + reward.getPoint())).build();
		return Box
				.builder().layout(FlexLayout.VERTICAL).spacing(FlexMarginSize.SM).contents(Arrays
						.asList(addToCartEnableButton))
				.build();
	}
}