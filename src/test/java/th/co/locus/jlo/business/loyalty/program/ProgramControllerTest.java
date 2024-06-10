package th.co.locus.jlo.business.loyalty.program;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import th.co.locus.jlo.business.loyalty.program.bean.ProgramCriteriaModelBean;
import th.co.locus.jlo.common.ApiPageRequest;
import th.co.locus.jlo.common.BaseController;

public class ProgramControllerTest extends BaseController {

	@Autowired
	private ProgramController pc;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testGetProgramList() {

		ProgramCriteriaModelBean pmb = new ProgramCriteriaModelBean();

		pmb.setProgramId(0L);
//		pmb.setProgramNo("");
		pmb.setProgramCode("");
		pmb.setProgram("");
		pmb.setActiveFlag("");
		pmb.setProgramStatus("");
//		pmb.setStartDate(LocalDate.now());
//		pmb.setEndDate(LocalDate.now());

		pmb.setPromotionCalculateRuleId("");
//		pmb.setPromotionCalculateRule("");
//		pmb.setPointExpireBasis("");
//		pmb.setPointExpireBasisId("");
//		pmb.setDescription("");

		ApiPageRequest apr = new ApiPageRequest(0, 10);
		apr.setData(pmb);

		pc.getProgramList(apr);

	}

	@Test
	public void testSaveProgram() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUserId() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPageRequest() {
		fail("Not yet implemented");
	}

}
