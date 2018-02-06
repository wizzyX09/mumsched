package edu.mum.mumsched.util;

import edu.mum.mumsched.model.Specialization;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SpecializationUtil {
    public static List<Specialization> specializations = new ArrayList<Specialization>(Arrays.asList(Specialization.values()));

    public static List<Specialization> getSpecializations(){
        return specializations;
    }
}
