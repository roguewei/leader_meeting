package com.winston.service;

import javax.servlet.http.HttpServletResponse;

public interface IImageService {

    void dropImage(HttpServletResponse response, int mId);

}
