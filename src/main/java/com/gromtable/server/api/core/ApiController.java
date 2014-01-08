package com.gromtable.server.api.core;

import com.gromtable.server.core.viewer.ViewerContext;

public abstract class ApiController<
    ControllerRequest extends BaseControllerRequest,
    ControllerResult extends BaseControllerResult> {
  public static final String INVALID_REQUEST = "Invalid request";
  public static final String NOT_LOGGED_IN = "Not logged in";
  private ApiRequest request;

  public void setRequest(ApiRequest request) {
    this.request = request;
  }

  private ApiRequest getRequest() {
    return request;
  }

  public abstract String getPath();

  /**
   * ApiController implement pattern factory. It can generate instances of itself.
   */
  public abstract ApiController<ControllerRequest, ControllerResult> createController();

  protected abstract ControllerRequest createControllerRequest(ApiRequest apiRequest);

  protected boolean getRequireLoggedIn() {
    return true;
  }

  /**
   * This method always should return not null object, even in case of invalid request.
   * Returned object will be used for generating documentation if needed.
   * @param request
   * @return not null object
   */
  protected abstract ControllerResult genControllerResult(ControllerRequest request);

  private final ApiResult getError(ControllerRequest controllerRequest, String error) {
    return new ApiResult(
      new BaseControllerResult().setError(error),
      controllerRequest.getCallback()
    );
  }

  public final ApiResult genResult() {
    ApiRequest apiRequest = getRequest();
    ViewerContext viewerContext = genViewerContext(apiRequest);
    ControllerRequest controllerRequest = createControllerRequest(apiRequest);
    controllerRequest.setViewerContext(viewerContext);
    // TODO: this look not cool
    if (getRequireLoggedIn() && !viewerContext.isLoggedIn()) {
      return getError(controllerRequest, NOT_LOGGED_IN);
    }
    boolean isValidRequest = controllerRequest.isValid();
    if (!isValidRequest) {
      return getError(controllerRequest, INVALID_REQUEST);
    }
    ControllerResult controllerResult = genControllerResult(controllerRequest);
    return new ApiResult(controllerResult, controllerRequest.getCallback());
  }

  protected ViewerContext genViewerContext(final ApiRequest request) {
    String sessionCookie = request.getSessionCookie();
    return ViewerContext.genCreateFromCookie(sessionCookie);
  }
}
