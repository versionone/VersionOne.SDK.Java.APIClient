const puppeteer = require('puppeteer');
const fsPromises = require('fs/promises');

(async () => {
    const fileContents = await fsPromises.readFile('EnvFile.properties');
    const env = {};
    fileContents.toString('utf8').split(/\r?\n/).forEach(line => {
        const parts = line.split('=');
        if (parts[0] && parts[1]) {
            env[parts[0]] = parts[1];
        }
    });
    env.V1_USERNAME = process.env.V1_USERNAME || env.V1_USERNAME;
    env.V1_PASSWORD = process.env.V1_PASSWORD || env.V1_PASSWORD;
    env.V1_INSTANCE_URL = process.env.V1_INSTANCE_URL || env.V1_INSTANCE_URL;

    const executablePath = "PUPPETEER_EXECUTABLE_PATH" in process.env ? process.env.PUPPETEER_EXECUTABLE_PATH : undefined;
    const browser = await puppeteer.launch({
        headless: true,
        executablePath,
        args: [
            '--no-sandbox',
            '--disable-setuid-sandbox',
            '--disable-features=AutomaticHttpsRewrites,HttpsUpgrades,HSTSPreloadedList,IsolatedWebApps',
            '--test-type',
            '--disable-extensions',
            '--disable-blink-features=AutomationControlled',
            '--disable-device-discovery-notifications',
            '--unsafely-treat-insecure-origin-as-secure=http://v1ultimate01.deftcn.net'
        ]
    });
    const page = await browser.newPage();

    page.on('requestfailed', (req) => {
        console.error('REQUEST FAILED:', req.url(), req.failure().errorText);
    });

    await page.goto(env.V1_INSTANCE_URL, { waitUntil: 'networkidle0' });
    await page.type('input[name="username"]', env.V1_USERNAME);
    await page.type('input[name="password"]', env.V1_PASSWORD);
    await Promise.all([
        page.waitForNavigation(),
        page.click('button[type="submit"]'),
      ]);
    await page.screenshot({path: 'after_login.png'});
    const accessTokenPath = '/Member.mvc/AccessTokenClient?oidToken=Member%3A20';
    await page.goto(`${env.V1_INSTANCE_URL}${accessTokenPath}`);
    try {
        await page.waitForSelector('input[name="client_name"]', {timeout: 90000});
    } catch (e) {
        await page.screenshot({path: 'timed_out_waiting_for_application_page.png'});
        throw e;
    }
    await page.type('input[name="client_name"]', 'Java SDK Test');
    await page.click('button[type="submit"]');
    await page.waitForNetworkIdle({ timeout: 15000 });
    await page.waitForSelector('input.access-token-value');
    const accessTokenInput = await page.$('input.access-token-value');
    const accessToken = await page.evaluate(accessTokenInput => accessTokenInput.value, accessTokenInput);
    env.V1_ACCESS_TOKEN = accessToken;
    // if (accessToken) {
    //     let envFile = '';
    //     for (const [key, value] of Object.entries(env)) {
    //         envFile += `${key}=${value}\n`;
    //     }
    //     await fsPromises.writeFile('EnvFile.properties', envFile);
    // }
    await browser.close();
    console.log(accessToken);
})();