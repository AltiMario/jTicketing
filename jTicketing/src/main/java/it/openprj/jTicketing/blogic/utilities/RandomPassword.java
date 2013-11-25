/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.blogic.utilities;

import java.util.Random;
import org.apache.log4j.Logger;

public class RandomPassword {
    private static Logger log =  Logger.getLogger(RandomPassword.class);
    private static final String charset = "!0A1B2C34K5L6Z7W89abOcPdTefQghLXijklmZnopqrsMNtuvBwxyz";

    /**
     * Metodo che crea una stringa casuale
     * @param length
     * @return
     */
    public static String getRandomString(int length) {
        log.debug("RandomPassword.getRandomString...start");
        Random rand = new Random(System.currentTimeMillis());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int pos = rand.nextInt(charset.length());
            sb.append(charset.charAt(pos));
        }
        log.debug("RandomPassword.getRandomString...stop");
        return sb.toString();
    }
   /*
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {

            System.out.println(RandomPassword.getRandomString(10));

            try {
                // if you generate more than 1 time, you must
                // put the process to sleep for awhile
                // otherwise it will return the same random string
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }*/

}