import java.math.BigDecimal;

/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : Savor Quartz AWS
 * @Title : Aaa.java
 * @Package :
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年7月5日
 * @Time : 下午9:54:24
 */

/**
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version
 * @notes Created on 2016年7月5日<br>
 *        Revision of last commit:$Revision$<br>
 *        Author of last commit:$Author$<br>
 *        Date of last commit:$Date$<br>
 *        <p />
 *
 */
public class TestLocation {

    public static void main(String[] args) {
        BigDecimal PI = new BigDecimal(3.1415926535897932384626433832795028841971693993);
        BigDecimal lanR = new BigDecimal(6356.755);
        BigDecimal lonR = new BigDecimal(6378.140);
        double lan = new BigDecimal(3600).divide(lanR.multiply(PI), 20, BigDecimal.ROUND_HALF_EVEN).doubleValue();
        double lon = new BigDecimal(3600).divide(lonR.multiply(PI), 20, BigDecimal.ROUND_HALF_EVEN).doubleValue();
        System.out.println(lan + "\t" + lon);
    }
}
