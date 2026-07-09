import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Page.GetByRoleOptions;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.SelectOption;


public class LaunchBrowserTest {


    private static final String URL = "https://www.saucedemo.com";
    private static Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;

    @BeforeMethod
    public void browserInvoke() {
        playwright = Playwright.create();

        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));

        context = browser.newContext();

        page = context.newPage();
    }

    @Test
    public void startBrowser() throws InterruptedException {


        page.navigate(URL);
        Locator username = page.getByRole(AriaRole.TEXTBOX, new GetByRoleOptions().setName("username"));
        Locator password = page.getByRole(AriaRole.TEXTBOX, new GetByRoleOptions().setName("password"));
        Locator loginBtn = page.getByRole(AriaRole.BUTTON, new GetByRoleOptions().setName("Login"));


        assertThat(username).isVisible();
        username.fill("standard_user");
        password.fill("secret_sauce");
        loginBtn.click();


        assertThat(page).hasTitle("Swag Labs");


        page.locator("[data-test='product-sort-container']").selectOption("hilo");

        page.evaluate("window.scrollTo(0, document.body.scrollHeight)");
        List<Locator> options = page.locator("//ul[@class=\"social\"]/li").all();

        for (Locator option : options) {

            Locator link = option.getByRole(AriaRole.LINK);
            String name = link.textContent().trim();

            if (name.equalsIgnoreCase("LinkedIn")) {
                link.click();
                break;
            }

        }


    }


    @AfterMethod
    public void tearDown() {

        context.close();
        browser.close();
        playwright.close();
    }

}
