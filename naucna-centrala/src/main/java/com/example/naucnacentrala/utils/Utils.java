package com.example.naucnacentrala.utils;

import com.example.naucnacentrala.dto.FormSubmissionDto;
import com.example.naucnacentrala.security.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

public class Utils {

    public static String getUsernameFromRequest(HttpServletRequest request, TokenUtils tokenUtils) {
        String authToken = tokenUtils.getToken(request);
        if (authToken == null) {
            return null;
        }

        String username = tokenUtils.getUsernameFromToken(authToken);
        return username;
    }

    public static HashMap<String, Object> mapListToDto(List<FormSubmissionDto> list) {

        HashMap<String, Object> map = new HashMap<String, Object>();
        for(FormSubmissionDto temp : list){
            map.put(temp.getFieldId(), temp.getFieldValue());
        }

        return map;
    }
}
