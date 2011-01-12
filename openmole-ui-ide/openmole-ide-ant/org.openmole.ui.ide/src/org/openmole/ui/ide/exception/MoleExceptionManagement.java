/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmole.ui.ide.exception;

/**
 *
 * @author mathieu
 */
public class MoleExceptionManagement {

    private static MoleExceptionManagement instance;

    public static void showException(Throwable e) {
        System.out.println("ShowException " + e + ", to be implemented");
    }

    public static void showException(String msg) {
        System.out.println("<ShowException :: \n" + msg + "\nto be implemented>");
    }

    public static void giveInformation(String msg) {
        System.out.println("<Info :: \n" + msg + "\nto be implemented>");
    }

    public static MoleExceptionManagement getInstance() {
        if (instance == null) {
            instance = new MoleExceptionManagement();
        }
        return instance;
    }
}
