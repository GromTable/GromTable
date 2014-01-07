package com.gromtable.server.servlet;

import java.util.HashMap;

import com.gromtable.server.api.Assert;
import com.gromtable.server.api.core.ApiController;

public class ApiControllers extends HashMap<String, ApiController<?, ?>> {
  private static final long serialVersionUID = 1L;

  public void add(ApiController<?, ?> apiController) {
    ApiController<?, ?> wasBefore = put(apiController.getPath(), apiController);
    Assert.assertNull(wasBefore, "Such count already exists");
  }
}
