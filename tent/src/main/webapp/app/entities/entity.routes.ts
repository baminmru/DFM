import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'changeRequestApp.adminAuthority.home.title' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  {
    path: 'request-type',
    data: { pageTitle: 'changeRequestApp.requestType.home.title' },
    loadChildren: () => import('./request-type/request-type.routes'),
  },
  {
    path: 'request-info',
    data: { pageTitle: 'changeRequestApp.requestInfo.home.title' },
    loadChildren: () => import('./request-info/request-info.routes'),
  },
  {
    path: 'request-content',
    data: { pageTitle: 'changeRequestApp.requestContent.home.title' },
    loadChildren: () => import('./request-content/request-content.routes'),
  },
  {
    path: 'request-config',
    data: { pageTitle: 'changeRequestApp.requestConfig.home.title' },
    loadChildren: () => import('./request-config/request-config.routes'),
  },
  {
    path: 'request-content-config',
    data: { pageTitle: 'changeRequestApp.requestContentConfig.home.title' },
    loadChildren: () => import('./request-content-config/request-content-config.routes'),
  },
  {
    path: 'request-param-dict',
    data: { pageTitle: 'changeRequestApp.requestParamDict.home.title' },
    loadChildren: () => import('./request-param-dict/request-param-dict.routes'),
  },
  {
    path: 'source-system',
    data: { pageTitle: 'changeRequestApp.sourceSystem.home.title' },
    loadChildren: () => import('./source-system/source-system.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
