/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.reelcash.data;

import com.google.code.reelcash.data.layout.fields.Resources;

/**
 * The exception is raised if someone tries to set make a field refer to
 * another field even if that wouldn't be possible.
 * 
 * @author andrei.olar
 */
public class InvalidFieldReferenceException extends RuntimeException {

    private static final long serialVersionUID = -2198678980843454422L;

    /**
     * Creates a new reference exception which states that the referer
     * can't refer the refered.
     *
     * @param referer the field which would like to refer to the refered field.
     * @param refered the field which would be refered to by the referer if the
     * reference would be valid.
     */
    public InvalidFieldReferenceException(String referer, String refered) {
        super(String.format(Resources.getString("invalidfieldreference"), refered, referer));
    }
}
