package com.gromtable.server.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gromtable.server.api.core.ApiController;
import com.gromtable.server.api.core.ApiResult;
import com.gromtable.server.api.core.HttpApiRequest;
import com.gromtable.server.api.createdocument.CreateDocumentController;
import com.gromtable.server.api.facebookcomments.FacebookCommentsController;
import com.gromtable.server.api.getdocumentinfo.GetDocumentController;
import com.gromtable.server.api.getdocumentslist.GetDocumentsListController;
import com.gromtable.server.api.getloggedinuser.GetLoggedinUserController;
import com.gromtable.server.api.getuserinfo.GetUserInfoController;
import com.gromtable.server.api.login.LoginController;
import com.gromtable.server.api.settings.SettingsController;
import com.gromtable.server.api.setuserinfo.SetUserInfoController;
import com.gromtable.server.api.votedocument.VoteDocumentController;
import com.gromtable.server.api.voteuser.VoteUserController;
import com.gromtable.server.core.environment.BaseEnvironment;
import com.gromtable.server.core.environment.ProductionEnvironment;
import com.gromtable.server.settings.Settings;

@SuppressWarnings("serial")
public class ApiServlet extends HttpServlet {
  private ApiController<?, ?> helpController;
  private ApiControllers controllers;

  /**
   * Creates a new ApiServlet object.
   */
  public ApiServlet() {
  }

  public void init(ServletConfig config) throws ServletException {
    super.init(config);

    BaseEnvironment.setEnvironment(new ProductionEnvironment(new Settings(config)));
    controllers = new ApiControllers();
    controllers.add(new LoginController());
    controllers.add(new VoteUserController());
    controllers.add(new GetUserInfoController());
    controllers.add(new SetUserInfoController());
    controllers.add(new SettingsController());
    controllers.add(new CreateDocumentController());
    controllers.add(new GetDocumentController());
    controllers.add(new VoteDocumentController());
    controllers.add(new GetDocumentsListController());
    controllers.add(new GetLoggedinUserController());
    controllers.add(new FacebookCommentsController());
  }

  private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String path = request.getPathInfo();
    System.out.println(request.toString());

    ApiController<?, ?> controllerFactory = controllers.get(path);

    if (controllerFactory == null) {
      controllerFactory = helpController;
    }
    HttpApiRequest apiRequest = new HttpApiRequest(request);
    ApiController<?, ?> controller = controllerFactory.createController();
    controller.setRequest(apiRequest);

    ApiResult apiResponse = controller.genResult();
    apiResponse.writeResponse(response);
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    handleRequest(request, response);
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    handleRequest(request, response);
  }
}
