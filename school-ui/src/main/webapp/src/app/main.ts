import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { Provider, APP_INITIALIZER } from '@angular/core';

import { AppModule } from './app.module';
import { ConfigurationLoader } from './services/configuration-loader.service'
import { IConfig } from './models/config.interface'


export function main(env: string) {
    let configurationLoader: ConfigurationLoader = new ConfigurationLoader();
    let config: IConfig = configurationLoader.load(env);
    platformBrowserDynamic([{ provide: 'Config', useValue: config }]).bootstrapModule(AppModule);
}