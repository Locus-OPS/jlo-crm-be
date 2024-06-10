package th.co.locus.jlo.integration.line.message;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.function.Supplier;

import com.linecorp.bot.model.action.URIAction;
import com.linecorp.bot.model.message.FlexMessage;
import com.linecorp.bot.model.message.flex.component.Box;
import com.linecorp.bot.model.message.flex.component.Button;
import com.linecorp.bot.model.message.flex.component.Button.ButtonHeight;
import com.linecorp.bot.model.message.flex.component.Icon;
import com.linecorp.bot.model.message.flex.component.Image;
import com.linecorp.bot.model.message.flex.component.Image.ImageAspectMode;
import com.linecorp.bot.model.message.flex.component.Image.ImageAspectRatio;
import com.linecorp.bot.model.message.flex.component.Separator;
import com.linecorp.bot.model.message.flex.component.Spacer;
import com.linecorp.bot.model.message.flex.component.Text;
import com.linecorp.bot.model.message.flex.container.Bubble;
import com.linecorp.bot.model.message.flex.unit.FlexFontSize;
import com.linecorp.bot.model.message.flex.unit.FlexLayout;
import com.linecorp.bot.model.message.flex.unit.FlexMarginSize;

public class RewardFlexMessageSupplier implements Supplier<FlexMessage> {

	@Override
	public FlexMessage get() {
		try {
			final Image heroBlock = createHeroBlock();
			final Box bodyBlock = createBodyBlock();
			final Box footerBlock = createFooterBlock();

			final Bubble bubbleContainer = Bubble.builder().hero(heroBlock).body(bodyBlock).footer(footerBlock).build();
			return new FlexMessage("Restaurant", bubbleContainer);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private Image createHeroBlock() throws URISyntaxException {
		return Image.builder().url(new URI("https://2553d2b9.ngrok.io/img/cafe.png")).size(Image.ImageSize.FULL_WIDTH)
				.aspectRatio(ImageAspectRatio.R20TO13).aspectMode(ImageAspectMode.Cover)
				.action(new URIAction("label", new URI("http://example.com"), null)).build();
	}

	private Box createBodyBlock() {
		final Text title = Text.builder().text("Brown Cafe").weight(Text.TextWeight.BOLD).size(FlexFontSize.XL).build();
		final Box review = createReviewBox();
		final Box info = createInfoBox();

		return Box.builder().layout(FlexLayout.VERTICAL).contents(Arrays.asList(title, review, info)).build();
	}

	private Box createInfoBox() {
		final Box place = Box.builder().layout(FlexLayout.BASELINE).spacing(FlexMarginSize.SM)
				.contents(Arrays.asList(
						Text.builder().text("Place").color("#aaaaaa").size(FlexFontSize.SM).flex(1).build(),
						Text.builder().text("Silom, Bangkok").wrap(true).color("#666666").flex(5).build()))
				.build();
		final Box time = Box.builder().layout(FlexLayout.BASELINE).spacing(FlexMarginSize.SM).contents(Arrays.asList(
				Text.builder().text("Time").color("#aaaaaa").size(FlexFontSize.SM).flex(1).build(),
				Text.builder().text("10:00 - 23:00").wrap(true).color("#666666").size(FlexFontSize.SM).flex(5).build()))
				.build();
		return Box.builder().layout(FlexLayout.VERTICAL).margin(FlexMarginSize.LG).spacing(FlexMarginSize.SM)
				.contents(Arrays.asList(place, time)).build();
	}

	private Box createReviewBox() {
		final Icon goldStar = Icon.builder().size(FlexFontSize.SM).url("https://2553d2b9.ngrok.io/img/gold_star.png")
				.build();
		final Icon grayStar = Icon.builder().size(FlexFontSize.SM).url("https://2553d2b9.ngrok.io/img/gray_star.png")
				.build();
		final Text point = Text.builder().text("4.0").size(FlexFontSize.SM).color("#999999").margin(FlexMarginSize.MD)
				.flex(0).build();

		return Box.builder().layout(FlexLayout.BASELINE).margin(FlexMarginSize.MD)
				.contents(Arrays.asList(goldStar, goldStar, goldStar, goldStar, grayStar, point)).build();
	}

	private Box createFooterBlock() throws URISyntaxException {
		final Spacer spacer = Spacer.builder().size(FlexMarginSize.SM).build();
		final Button callAction = Button.builder().style(Button.ButtonStyle.LINK).height(ButtonHeight.MEDIUM)
				.action(new URIAction("CALL", new URI("tel:00000"), null)).build();
		final Separator separator = Separator.builder().build();
		final Button websiteAction = Button.builder().style(Button.ButtonStyle.LINK).height(ButtonHeight.SMALL)
				.action(new URIAction("WEBSITE", new URI("https://example.com"), null)).build();

		return Box.builder().layout(FlexLayout.VERTICAL).spacing(FlexMarginSize.SM)
				.contents(Arrays.asList(spacer, callAction, separator, websiteAction)).build();

	}
}