package com.abdullah.home.automation.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.abdullah.home.automation.domain.Payload2;
import com.abdullah.home.automation.domain.WeatherData;
import com.abdullah.home.automation.service.PayloadService;
import org.springframework.stereotype.Service;

@Service
public class PayloadServiceImpl implements PayloadService {

	@Override
	public List<Payload2> buildPayloadFromList(List<WeatherData> weatherDataSet, String payloadType) {
		List<Payload2> payload2List = new ArrayList<>(23);

		for(int i = 0 ; i< 24 ; i++) {
			payload2List.add(new Payload2());
		}

		for (WeatherData wd : weatherDataSet) {

			if (wd.getTime().contains("00:00:00")) {
				// 0
				payload2List = splitPayload(payload2List, wd, 0, payloadType);
			}
			if (wd.getTime().contains("01:00:00")) {
				// 1
				payload2List = splitPayload(payload2List, wd, 1, payloadType);
			}
			if (wd.getTime().contains("02:00:00")) {
				// 1
				payload2List = splitPayload(payload2List, wd, 2, payloadType);
			}
			if (wd.getTime().contains("03:00:00")) {
				// 1
				payload2List = splitPayload(payload2List, wd, 3, payloadType);
			}
			if (wd.getTime().contains("04:00:00")) {
				// 1
				payload2List = splitPayload(payload2List, wd, 4,payloadType);
			}
			if (wd.getTime().contains("05:00:00")) {
				// 1
				payload2List = splitPayload(payload2List, wd, 5, payloadType);
			}
			if (wd.getTime().contains("06:00:00")) {
				// 1
				payload2List = splitPayload(payload2List, wd, 6, payloadType);
			}
			if (wd.getTime().contains("07:00:00")) {
				// 1
				payload2List = splitPayload(payload2List, wd, 7, payloadType);
			}
			if (wd.getTime().contains("08:00:00")) {
				// 1
				payload2List = splitPayload(payload2List, wd, 8, payloadType);
			}
			if (wd.getTime().contains("09:00:00")) {
				// 1
				payload2List = splitPayload(payload2List, wd, 9,payloadType);
			}
			if (wd.getTime().contains("10:00:00")) {
				// 1
				payload2List = splitPayload(payload2List, wd, 10, payloadType);
			}
			if (wd.getTime().contains("11:00:00")) {
				// 1
				payload2List = splitPayload(payload2List, wd, 11, payloadType);
			}
			if (wd.getTime().contains("12:00:00")) {
				// 1
				payload2List = splitPayload(payload2List, wd, 12, payloadType);
			}
			if (wd.getTime().contains("13:00:00")) {
				// 1
				payload2List = splitPayload(payload2List, wd, 13, payloadType);
			}
			if (wd.getTime().contains("14:00:00")) {
				// 1
				payload2List = splitPayload(payload2List, wd, 14, payloadType);
			}
			if (wd.getTime().contains("15:00:00")) {
				// 1
				payload2List = splitPayload(payload2List, wd, 15, payloadType);
			}
			if (wd.getTime().contains("16:00:00")) {
				// 1
				payload2List = splitPayload(payload2List, wd, 16, payloadType);
			}
			if (wd.getTime().contains("17:00:00")) {
				// 1
				payload2List = splitPayload(payload2List, wd, 17, payloadType);
			}
			if (wd.getTime().contains("18:00:00")) {
				// 1
				payload2List = splitPayload(payload2List, wd, 18 , payloadType);
			}
			if (wd.getTime().contains("19:00:00")) {
				// 1
				payload2List = splitPayload(payload2List, wd, 19, payloadType);
			}
			if (wd.getTime().contains("20:00:00")) {
				// 1
				payload2List = splitPayload(payload2List, wd, 20, payloadType);
			}
			if (wd.getTime().contains("21:00:00")) {
				// 1
				payload2List = splitPayload(payload2List, wd, 21, payloadType);
			}
			if (wd.getTime().contains("22:00:00")) {
				// 1
				payload2List = splitPayload(payload2List, wd, 22, payloadType);
			}
			if (wd.getTime().contains("23:00:00")) {
				// 1
				payload2List = splitPayload(payload2List, wd, 23, payloadType);
			}
		}

		// payload2list.forEach(n -> System.out.println(n.toString()));

		return payload2List;
	}
	
	
	private List<Payload2> splitPayload(List<Payload2> payload2List, WeatherData wd, int i, String payloadType) {
		payload2List.get(i).setHourName("h " + i);



		double payloadParameter;

		if (payloadType.equals("humidity")){
			payloadParameter = wd.getHumidity();
		}

		else if(payloadType.equals("temperature")){
			payloadParameter = wd.getTemperature();
		}
		else if(payloadType.equals("pressure")){
			payloadParameter = wd.getPressure();
		}
		else if(payloadType.equals("winddirection")){
			payloadParameter = wd.getWinddirection();
		}
		else if(payloadType.equals("windspeed")){
			payloadParameter = wd.getWindspeed();
		}
		else if(payloadType.equals("precipitation")){
			payloadParameter = wd.getPrecipitation();
		}
		else if(payloadType.equals("dewpoint")){
			payloadParameter = wd.getDewpoint();
		}else{
			payloadParameter = 0;
		}

		if (wd.getTime().substring(8, 10).equals("01")) {
			// System.out.println(wd.getTime().substring(8,10));
			payload2List.get(i).setD1(payloadParameter);// get(0) -> h1
		}
		if (wd.getTime().substring(8, 10).equals("02")) {
			// System.out.println(wd.getTime().substring(8,10));
			payload2List.get(i).setD2(payloadParameter);// get(0) -> h1
		}
		if (wd.getTime().substring(8, 10).equals("03")) {
			// System.out.println(wd.getTime().substring(8,10));
			payload2List.get(i).setD3(payloadParameter);// get(0) -> h1
		}
		if (wd.getTime().substring(8, 10).equals("04")) {
			// System.out.println(wd.getTime().substring(8,10));
			payload2List.get(i).setD4(payloadParameter);// get(0) -> h1
		}
		if (wd.getTime().substring(8, 10).equals("05")) {
			// System.out.println(wd.getTime().substring(8,10));
			payload2List.get(i).setD5(payloadParameter);// get(0) -> h1
		}
		if (wd.getTime().substring(8, 10).equals("06")) {
			// System.out.println(wd.getTime().substring(8,10));
			payload2List.get(i).setD6(payloadParameter);// get(0) -> h1
		}
		if (wd.getTime().substring(8, 10).equals("07")) {
			// System.out.println(wd.getTime().substring(8,10));
			payload2List.get(i).setD7(payloadParameter);// get(0) -> h1
		}
		if (wd.getTime().substring(8, 10).equals("08")) {
			// System.out.println(wd.getTime().substring(8,10));
			payload2List.get(i).setD8(payloadParameter);// get(0) -> h1
		}
		if (wd.getTime().substring(8, 10).equals("09")) {
			// System.out.println(wd.getTime().substring(8,10));
			payload2List.get(i).setD9(payloadParameter);// get(0) -> h1
		}
		if (wd.getTime().substring(8, 10).equals("10")) {
			// System.out.println(wd.getTime().substring(8,10));
			payload2List.get(i).setD10(payloadParameter);// get(0) -> h1
		}
		if (wd.getTime().substring(8, 10).equals("11")) {
			// System.out.println(wd.getTime().substring(8,10));
			payload2List.get(i).setD11(payloadParameter);// get(0) -> h1
		}
		if (wd.getTime().substring(8, 10).equals("12")) {
			// System.out.println(wd.getTime().substring(8,10));
			payload2List.get(i).setD12(payloadParameter);// get(0) -> h1
		}
		if (wd.getTime().substring(8, 10).equals("13")) {
			// System.out.println(wd.getTime().substring(8,10));
			payload2List.get(i).setD13(payloadParameter);// get(0) -> h1
		}
		if (wd.getTime().substring(8, 10).equals("14")) {
			// System.out.println(wd.getTime().substring(8,10));
			payload2List.get(i).setD14(payloadParameter);// get(0) -> h1
		}
		if (wd.getTime().substring(8, 10).equals("15")) {
			// System.out.println(wd.getTime().substring(8,10));
			payload2List.get(i).setD15(payloadParameter);// get(0) -> h1
		}
		if (wd.getTime().substring(8, 10).equals("16")) {
			// System.out.println(wd.getTime().substring(8,10));
			payload2List.get(i).setD16(payloadParameter);// get(0) -> h1
		}
		if (wd.getTime().substring(8, 10).equals("17")) {
			// System.out.println(wd.getTime().substring(8,10));
			payload2List.get(i).setD17(payloadParameter);// get(0) -> h1
		}
		if (wd.getTime().substring(8, 10).equals("18")) {
			// System.out.println(wd.getTime().substring(8,10));
			payload2List.get(i).setD18(payloadParameter);// get(0) -> h1
		}
		if (wd.getTime().substring(8, 10).equals("19")) {
			// System.out.println(wd.getTime().substring(8,10));
			payload2List.get(i).setD19(payloadParameter);// get(0) -> h1
		}
		if (wd.getTime().substring(8, 10).equals("20")) {
			// System.out.println(wd.getTime().substring(8,10));
			payload2List.get(i).setD20(payloadParameter);// get(0) -> h1
		}
		if (wd.getTime().substring(8, 10).equals("21")) {
			// System.out.println(wd.getTime().substring(8,10));
			payload2List.get(i).setD21(payloadParameter);// get(0) -> h1
		}
		if (wd.getTime().substring(8, 10).equals("22")) {
			// System.out.println(wd.getTime().substring(8,10));
			payload2List.get(i).setD22(payloadParameter);// get(0) -> h1
		}
		if (wd.getTime().substring(8, 10).equals("23")) {
			// System.out.println(wd.getTime().substring(8,10));
			payload2List.get(i).setD23(payloadParameter);// get(0) -> h1
		}
		if (wd.getTime().substring(8, 10).equals("24")) {
			// System.out.println(wd.getTime().substring(8,10));
			payload2List.get(i).setD24(payloadParameter);// get(0) -> h1
		}
		if (wd.getTime().substring(8, 10).equals("25")) {
			// System.out.println(wd.getTime().substring(8,10));
			payload2List.get(i).setD25(payloadParameter);// get(0) -> h1
		}
		if (wd.getTime().substring(8, 10).equals("26")) {
			// System.out.println(wd.getTime().substring(8,10));
			payload2List.get(i).setD26(payloadParameter);// get(0) -> h1
		}
		if (wd.getTime().substring(8, 10).equals("27")) {
			// System.out.println(wd.getTime().substring(8,10));
			payload2List.get(i).setD27(payloadParameter);// get(0) -> h1
		}
		if (wd.getTime().substring(8, 10).equals("28")) {
			// System.out.println(wd.getTime().substring(8,10));
			payload2List.get(i).setD28(payloadParameter);// get(0) -> h1
		}
		if (wd.getTime().substring(8, 10).equals("29")) {
			// System.out.println(wd.getTime().substring(8,10));
			payload2List.get(i).setD29(payloadParameter);// get(0) -> h1
		}
		if (wd.getTime().substring(8, 10).equals("30")) {
			// System.out.println(wd.getTime().substring(8,10));
			payload2List.get(i).setD30(payloadParameter);// get(0) -> h1
		}
		if (wd.getTime().substring(8, 10).equals("31")) {
			// System.out.println(wd.getTime().substring(8,10));
			payload2List.get(i).setD31(payloadParameter);// get(0) -> h1
		}
		return payload2List;
	}

}
