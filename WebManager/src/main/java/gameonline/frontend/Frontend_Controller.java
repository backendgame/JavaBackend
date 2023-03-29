package gameonline.frontend;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestParam;

@Api(tags = "Frontend", description = "Web")
@Controller
public class Frontend_Controller {
    @GetMapping(value = "/register", produces = MediaType.TEXT_HTML_VALUE)
    public String registerUserPage(Model model) {
        return "RegisterPage";
    }
    @GetMapping(value = "/login", produces = MediaType.TEXT_HTML_VALUE)
    public String loginUserPage(Model model) {
        return "LoginPage";
    }
    @GetMapping(value = "/user/data", produces = MediaType.TEXT_HTML_VALUE)
    public String databaseDetails(Model model, @RequestParam final String dbId) {
        System.out.print("i: " + dbId);
        // todo: get data and return to ui base on id provide by url
        return "DatabaseDetails";
    }
    @GetMapping(value = "/user/db-config", produces = MediaType.TEXT_HTML_VALUE)
    public String databaseInfo(Model model, @RequestParam final String dbId) {
        // todo: get data and return to ui base on id provide by url
        return "DatabaseConfig";
    }
    @GetMapping(value = "/user/tile-row", produces = MediaType.TEXT_HTML_VALUE)
    public String tileRow(Model model, @RequestParam final String dbId) {
        // todo: get data and return to ui base on id provide by url
        return "TileRow";
    }
    @GetMapping(value = "/user/tile-custom", produces = MediaType.TEXT_HTML_VALUE)
    public String tileCustom(Model model, @RequestParam final String dbId) {
        // todo: get data and return to ui base on id provide by url
        return "TileCustom";
    }
    @GetMapping(value = "/user", produces = MediaType.TEXT_HTML_VALUE)
    public String lobbyUserPage(Model model) {
        return "UserDashboard";
    }
    @GetMapping(value = "/index", produces = MediaType.TEXT_HTML_VALUE)
    public String indexPage(Model model) {
        return "index";
    }

    @GetMapping(value = "/payment-review", produces = MediaType.TEXT_HTML_VALUE)
    public String paymentReview(Model model) {
        return "PaymentReview";
    }

    @GetMapping(value = "/forbidden", produces = MediaType.TEXT_HTML_VALUE)
    public String forbiddenPage(Model model) {
        return "Forbidden";
    }

    @GetMapping(value = "/tiles", produces = MediaType.TEXT_HTML_VALUE)
    public String tilesPage(Model model) {
        return "TilesPage";
    }

    @GetMapping(value = "/user/leaderboard", produces = MediaType.TEXT_HTML_VALUE)
    public String leaderboard(Model model) {
        return "Leaderboard";
    }

    @GetMapping(value = "/top-up", produces = MediaType.TEXT_HTML_VALUE)
    public String topUp(Model model) {
        return "TopUp";
    }
}
