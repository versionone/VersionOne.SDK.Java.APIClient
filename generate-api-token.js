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

    const browser = await puppeteer.launch({
        headless: true,
        args: ['--no-sandbox', '--disable-setuid-sandbox']
    });
    const page = await browser.newPage();
    await page.goto(env.V1_INSTANCE_URL);
    await page.type('input[name="username"]', env.V1_USERNAME);
    await page.type('input[name="password"]', env.V1_PASSWORD);
    await page.click('button[type="submit"]');
    // await page.screenshot({path: 'screenshot1.png'});
    const accessTokenPath = '/Member.mvc/AccessTokenClient?oidToken=Member%3A20';
    await page.goto(`${env.V1_INSTANCE_URL}${accessTokenPath}`);
    await page.waitForSelector('input[name="client_name"]');
    await page.type('input[name="client_name"]', 'Java SDK Test');
    await page.click('button[type="submit"]');
    await page.waitForNetworkIdle({ timeout: 2000 });
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