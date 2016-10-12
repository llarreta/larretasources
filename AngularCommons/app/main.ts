import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { AppModule } from './Modules/app.module';
const platform = platformBrowserDynamic();
platform.bootstrapModule(AppModule);
