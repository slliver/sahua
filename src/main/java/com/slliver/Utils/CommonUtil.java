package com.slliver.Utils;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class CommonUtil {

	public static boolean getChance(int percentage, int max) {
		Random random = new Random();
		int i = random.nextInt(max - 1);

		return (i >= 0 && i < percentage);
	}

	public static String createRandomCharData(int length)
	{
		StringBuilder sb=new StringBuilder();
		Random rand=new Random();//随机用以下三个随机生成器
		Random randdata=new Random();
		int data=0;
		for(int i=0;i<length;i++)
		{
			int index=rand.nextInt(3);
			//目的是随机选择生成数字，大小写字母
			switch(index)
			{
				case 0:
					data=randdata.nextInt(10);//仅仅会生成0~9
					sb.append(data);
					break;
				case 1:
					data=randdata.nextInt(26)+65;//保证只会产生65~90之间的整数
					sb.append((char)data);
					break;
				case 2:
					data=randdata.nextInt(26)+97;//保证只会产生97~122之间的整数
					sb.append((char)data);
					break;
			}
		}
		String result=sb.toString();
		return result;
	}

	public static long getStartTime() {
		Calendar todayStart = Calendar.getInstance();
		todayStart.setTime(new Date());
		todayStart.set(Calendar.HOUR_OF_DAY, 0);
		todayStart.set(Calendar.MINUTE, 0);
		todayStart.set(Calendar.SECOND, 0);
		todayStart.set(Calendar.MILLISECOND, 0);
		System.out.println("start=" + todayStart.getTime());
		return todayStart.getTimeInMillis();
	}

	public static long getEndTime() {
		Calendar todayEnd = Calendar.getInstance();
		todayEnd.setTime(new Date());
		todayEnd.set(Calendar.HOUR_OF_DAY, 23);
		todayEnd.set(Calendar.MINUTE, 59);
		todayEnd.set(Calendar.SECOND, 59);
		todayEnd.set(Calendar.MILLISECOND, 999);
		System.out.println("end=" + todayEnd.getTime());
		return todayEnd.getTimeInMillis();
	}

//	public static void main(String[] args) {
//		getStartTime();
//		getEndTime();
//	}
}
