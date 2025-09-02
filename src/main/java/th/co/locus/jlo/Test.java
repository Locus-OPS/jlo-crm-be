package th.co.locus.jlo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Test {

	public static void main(String[] args) throws ParseException {

		// System.out.println(checkExpiryDate(new Date()));
		// System.out.println(generateHashKeyUID());
		String subjectEmail = "Case [#CASE-20241003-00003] -สอบถามสถานะการสมัครบัตร";
		System.out.println("Contains : " + checkCaseExisting(subjectEmail));
		getCaseNumberFromSubjectMail(subjectEmail);
	}

	private static String getCaseNumberFromSubjectMail(String emailSubject) {
		
		int startIndex = emailSubject.indexOf("#");
		int endIndex = emailSubject.indexOf("]");
		System.out.println(startIndex+" :"+endIndex);
		String caseString = emailSubject.substring(startIndex+1, endIndex);
		System.out.println("caseString :"+caseString);
		 

		return caseString;
	}

	private static boolean checkCaseExisting(String emailSubject) {
		return emailSubject.contains("#CASE");
	}

	@SuppressWarnings("unused")
	private static String generateHashKeyUID() {
		System.out.println(UUID.randomUUID().toString().length());
		return UUID.randomUUID().toString();
	}

	@SuppressWarnings("unused")
	private static boolean checkExpiryDate(Date input) throws ParseException {

//	 	String input = "2024-08-22 16:50:10";//"2024-08-21 17:46:58";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		simpleDateFormat.setLenient(false);
		Date expiry = input;// simpleDateFormat.parse(input);

		boolean expired = expiry.before(new Date());
		if (expired == true) {
			System.out.println("This card has already expired");
			return true;

		} else {
			System.out.println("This card has not expired");
			return false;
		}

	}

}
