package com.gromtable.server.api.core;

import com.gromtable.server.core.entity.ErrorType;
import com.gromtable.server.core.viewer.ViewerContext;

public abstract class ApiController<
    ControllerRequest extends BaseControllerRequest,
    ControllerResult extends BaseControllerResult> {
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

  private final ApiResult getError(ControllerRequest controllerRequest, ErrorType error) {
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
      return getError(controllerRequest, ErrorType.NOT_LOGGED_IN);
    }
    boolean isValidRequest = controllerRequest.isValid();
    if (!isValidRequest) {
      return getError(controllerRequest, ErrorType.INVALID_REQUEST);
    }
    try {
      ControllerResult controllerResult = genControllerResult(controllerRequest);
      return new ApiResult(controllerResult, controllerRequest.getCallback());
    } catch (Exception exception) {
      System.out.println(exception);
      return getError(controllerRequest, ErrorType.UKNOWN_ERROR);
    }
  }

  protected ViewerContext genViewerContext(final ApiRequest request) {
    String sessionCookie = request.getSessionCookie();
    return ViewerContext.genCreateFromCookie(sessionCookie);
  }
}
