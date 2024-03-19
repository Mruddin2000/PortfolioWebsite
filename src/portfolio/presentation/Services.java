package portfolio.presentation;

import portfolio.common.AboutRepository;
import portfolio.common.ExperienceRepositoryBackend;
import portfolio.common.ExperienceRepositoryFrontend;
import portfolio.common.LoginRepository;
import portfolio.common.PortfolioRepository;
import portfolio.data.sqlite.SqliteAboutRepository;
import portfolio.data.sqlite.SqliteConnection;
import portfolio.data.sqlite.SqliteExperienceRepositoryBackEnd;
import portfolio.data.sqlite.SqliteExperienceRepositoryFrontEnd;
import portfolio.data.sqlite.SqliteLoginRepository;
import portfolio.data.sqlite.SqlitePortfolioRepository;
import portfolio.logic.AboutLogic;
import portfolio.logic.ExperienceLogic;
import portfolio.logic.HomeLogic;
import portfolio.logic.LoginLogic;
import portfolio.logic.PortfolioLogic;
import portfolio.presentation.controllers.AboutController;
import portfolio.presentation.controllers.AdminController;
import portfolio.presentation.controllers.ExperienceBackEndController;
import portfolio.presentation.controllers.ExperienceFrontEndController;
import portfolio.presentation.controllers.HomeController;
import portfolio.presentation.controllers.PortfolioController;
import portfolio.presentation.controllers.admin.AboutAdmin;
import portfolio.presentation.controllers.admin.CheckPassword;
import portfolio.presentation.controllers.admin.ExperienceBackEndAdmin;
import portfolio.presentation.controllers.admin.ExperienceFrontEndAdmin;
import portfolio.presentation.controllers.admin.PortfolioAdmin;
import portfolio.presentation.controllers.admin.ResetMe;
import portfolio.presentation.controllers.admin.backendEndpointController;

public class Services {

	public ExperienceRepositoryFrontend experienceRepositoryFrontEnd;
	public ExperienceRepositoryBackend experienceRepositoryBackEnd;
	public AboutRepository aboutRepository;
	public PortfolioRepository portfolioRepository;
	public LoginRepository loginRepository;
	// public LoginRepository loginRepository;

	public ExperienceLogic experienceLogic;
	public PortfolioLogic portfolioLogic;
	public HomeLogic homeLogic;
	public AboutLogic aboutLogic;
	public LoginLogic loginLogic;
	// public AdminLogic adminLogic;

	public HomeController homeController;
	public ExperienceFrontEndController experienceController;
	public ExperienceBackEndController experienceBackEndController;
	public backendEndpointController backendEndpointController;
	public PortfolioAdmin portfolioAdminController;
	public AboutAdmin aboutAdminController;
	public CheckPassword checkPasswordLogin;
	public AdminController adminController;
	public ResetMe resetPassword;
	public ExperienceFrontEndAdmin experienceFrontEndAdminController;
	public ExperienceBackEndAdmin experienceBackEndAdminController;

	public AboutController aboutController;
	public PortfolioController portfolioController;

	// public AdminController adminController;
	// public PortfolioControllerAdmin portfolioControllerAdmin;
//	public Controller AdminController;

	Services() {
		SqliteConnection connection = new SqliteConnection("portfolioDb.sqlite");

		experienceRepositoryFrontEnd = new SqliteExperienceRepositoryFrontEnd(connection);
		experienceRepositoryBackEnd = new SqliteExperienceRepositoryBackEnd(connection);
		// aboutRepository = new SqliteAboutRepository(connection);
		portfolioRepository = new SqlitePortfolioRepository(connection);
		aboutRepository = new SqliteAboutRepository(connection);
		loginRepository = new SqliteLoginRepository(connection);

		experienceLogic = new ExperienceLogic(experienceRepositoryFrontEnd, experienceRepositoryBackEnd);
		// applicationLogic = new AboutLogic(applicationRepository);
		aboutLogic = new AboutLogic(aboutRepository);
		portfolioLogic = new PortfolioLogic(portfolioRepository);
		loginLogic = new LoginLogic(loginRepository);
		// adminLogic = new AdminLogic();

		homeController = new HomeController(homeLogic, aboutLogic, portfolioLogic, experienceLogic);
		experienceController = new ExperienceFrontEndController(experienceLogic);
		experienceBackEndController = new ExperienceBackEndController(experienceLogic);
		aboutController = new AboutController(aboutLogic);
		portfolioController = new PortfolioController(portfolioLogic);
		backendEndpointController = new backendEndpointController();
		portfolioAdminController = new PortfolioAdmin(portfolioLogic);
		aboutAdminController = new AboutAdmin(aboutLogic);
		checkPasswordLogin = new CheckPassword(loginLogic);
		adminController = new AdminController();
		resetPassword = new ResetMe(loginLogic);

		experienceFrontEndAdminController = new ExperienceFrontEndAdmin(experienceLogic);
		experienceBackEndAdminController = new ExperienceBackEndAdmin(experienceLogic);

		// portfolioControllerAdmin = new PortfolioControllerAdmin(portfolioLogic);
		// adminController = new AdminController();
		// AdminController = new Controller();
	}

}