import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'testTentApp.adminAuthority.home.title' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  {
    path: 'request-type',
    data: { pageTitle: 'testTentApp.requestType.home.title' },
    loadChildren: () => import('./request-type/request-type.routes'),
  },
  {
    path: 'request-info',
    data: { pageTitle: 'testTentApp.requestInfo.home.title' },
    loadChildren: () => import('./request-info/request-info.routes'),
  },
  {
    path: 'request-content',
    data: { pageTitle: 'testTentApp.requestContent.home.title' },
    loadChildren: () => import('./request-content/request-content.routes'),
  },
  {
    path: 'request-config',
    data: { pageTitle: 'testTentApp.requestConfig.home.title' },
    loadChildren: () => import('./request-config/request-config.routes'),
  },
  {
    path: 'request-content-config',
    data: { pageTitle: 'testTentApp.requestContentConfig.home.title' },
    loadChildren: () => import('./request-content-config/request-content-config.routes'),
  },
  {
    path: 'request-param-dict',
    data: { pageTitle: 'testTentApp.requestParamDict.home.title' },
    loadChildren: () => import('./request-param-dict/request-param-dict.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
