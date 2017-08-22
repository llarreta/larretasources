import { Http, Headers, Response, RequestOptions } from '@angular/http';
import { IConfig } from '../models/config.interface';

export class ConfigurationLoader {

    private CONF: { [id: string] : IConfig } = {
        "DEV": {
            APP_URL: "http://localhost:3000/",
            API_URL: "http://localhost:8089" + "/school/",
            LOG_LEVEL: "ALL",
            MOCK: true
        },
        "PROD":{
            APP_URL: "http://localhost:3000/",
            API_URL: window.location.origin + "/school/",
            LOG_LEVEL: "NONE",
            MOCK: false
        }
    };

    load(env: string): IConfig{
        let envConf = this.CONF[env];
        return  {
            APP_URL: envConf.APP_URL,
            API_URL: envConf.API_URL,
            LOG_LEVEL: envConf.LOG_LEVEL,
            MOCK: envConf.MOCK
        }
    }

}