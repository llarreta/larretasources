(function(global) {
    System.config({
        paths: {
            'npm:': 'node_modules/'
        },
        map: {
            app: 'src/app',
            '@angular/core': 'npm:@angular/core/bundles/core.umd.js',
            '@angular/animations': 'npm:@angular/animations/bundles/animations.umd.js',
            '@angular/animations/browser': 'npm:@angular/animations/bundles/animations-browser.umd.js',
            '@angular/platform-browser/animations': 'npm:@angular/platform-browser/bundles/platform-browser-animations.umd.js',   
            '@angular/common': 'npm:@angular/common/bundles/common.umd.js',
            '@angular/compiler': 'npm:@angular/compiler/bundles/compiler.umd.js',
            '@angular/platform-browser': 'npm:@angular/platform-browser/bundles/platform-browser.umd.js',
            '@angular/platform-browser-dynamic': 'npm:@angular/platform-browser-dynamic/bundles/platform-browser-dynamic.umd.js',
            'rxjs': 'npm:rxjs',
            '@angular/http': 'npm:@angular/http/bundles/http.umd.js',
            '@angular/router': 'npm:@angular/router/bundles/router.umd.js',
            '@angular/forms': 'npm:@angular/forms/bundles/forms.umd.js',
            'primeng': 'npm:primeng',
            'text-mask-core': 'npm:text-mask-core',
            'angular2-text-mask': 'npm:angular2-text-mask/dist/angular2TextMask.js',
            'text-mask-addons': 'npm:text-mask-addons'

        },
        packages: {
            app: {
                main: './main.js',
                defaultExtension: 'js'
            },
            rxjs: {
                main: './bundles/Rx.min.js',
                defaultExtension: 'js'
            },
            primeng: {
                defaultExtension: 'js'
            },
            'text-mask-core': {
                defaultExtension: 'js'
            },
            'angular2-text-mask': {
                defaultExtension: 'js'
            },
            'text-mask-addons': {
                main: './dist/textMaskAddons.js',
                defaultExtension: 'js'
            }
        }
    });
})(this);
