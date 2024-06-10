package th.co.locus.jlo.integration.line.message;

import java.text.NumberFormat;
import java.util.Arrays;

import com.linecorp.bot.model.action.PostbackAction;
import com.linecorp.bot.model.message.FlexMessage;
import com.linecorp.bot.model.message.flex.component.Box;
import com.linecorp.bot.model.message.flex.component.Button;
import com.linecorp.bot.model.message.flex.component.Separator;
import com.linecorp.bot.model.message.flex.component.Text;
import com.linecorp.bot.model.message.flex.container.Bubble;
import com.linecorp.bot.model.message.flex.unit.FlexAlign;
import com.linecorp.bot.model.message.flex.unit.FlexFontSize;
import com.linecorp.bot.model.message.flex.unit.FlexLayout;
import com.linecorp.bot.model.message.flex.unit.FlexMarginSize;

import th.co.locus.jlo.business.redemption.bean.RedemptionMemberInfoModelBean;

public class MemberInfoFlexMessageBuilder {
	
	private static NumberFormat numberFormat = NumberFormat.getNumberInstance();
	
	public static FlexMessage build(RedemptionMemberInfoModelBean memberInfo) {
        final Separator separator = Separator.builder().margin(FlexMarginSize.XXL).build();
        final Box headerBlock = createBodyHeaderBox(memberInfo);
        final Box itemBlock = createBodyItemBlock(memberInfo);
        final Box rewardBlock = createFooterBlock(memberInfo);

        final Box bodyBlock = Box.builder()
                .layout(FlexLayout.VERTICAL)
                .contents(Arrays.asList(
                        headerBlock,
                        separator,
                        itemBlock
//                        separator,
//                        rewardBlock
                        ))
                .build();

        final Bubble bubble = Bubble.builder()
                .body(bodyBlock)
                .build();
        return new FlexMessage("ALT", bubble);
    }

    private static Box createBodyHeaderBox(RedemptionMemberInfoModelBean memberInfo) {
        final Text bodyHeaderText = Text.builder()
                .text("Member Information")
                .weight(Text.TextWeight.BOLD)
                .color("#1db446")
                .size(FlexFontSize.SM)
                .build();
        final Text bodyTitleHeaderText = Text.builder()
                .text(memberInfo.getFirstName() + " " + memberInfo.getLastName())
                .weight(Text.TextWeight.BOLD)
                .size(FlexFontSize.XL)
                .margin(FlexMarginSize.MD)
                .build();
        final Text bodyTitleHeaderDetail = Text.builder()
                .text(memberInfo.getTierName())
                .size(FlexFontSize.XS)
                .color("#aaaaaa")
                .wrap(true)
                .build();

        return Box.builder()
                .layout(FlexLayout.VERTICAL)
                .contents(Arrays.asList(
                        bodyHeaderText,
                        bodyTitleHeaderText,
                        bodyTitleHeaderDetail))
                .build();
    }

    private static Box createBodyItemBlock(RedemptionMemberInfoModelBean memberInfo) {
        final Box item1 = createItem("Current Point", numberFormat.format(memberInfo.getCurrentPoint().longValue()));
        return Box.builder()
                .layout(FlexLayout.VERTICAL)
                .margin(FlexMarginSize.XXL)
                .spacing(FlexMarginSize.SM)
                .contents(Arrays.asList(item1))
                .build();
    }

    private static Box createItem(String name, String price) {
        return Box.builder()
                .layout(FlexLayout.HORIZONTAL)
                .contents(Arrays.asList(
                        Text.builder()
                                .text(name)
                                .size(FlexFontSize.SM)
                                .color("#555555")
                                .flex(0)
                                .build(),
                        Text.builder()
                                .text(price)
                                .size(FlexFontSize.SM)
                                .color("#111111")
                                .align(FlexAlign.END)
                                .build()
                )).build();
    }
    
    private static Box createFooterBlock(RedemptionMemberInfoModelBean memberInfo) {
		final Button rewardListBtn = Button.builder().style(Button.ButtonStyle.PRIMARY)
				.action(new PostbackAction("Reward List", "reward-list:" + memberInfo.getMemberId())).build();
		return Box
				.builder().layout(FlexLayout.VERTICAL).spacing(FlexMarginSize.SM).contents(Arrays.asList(rewardListBtn))
				.build();
	}
}