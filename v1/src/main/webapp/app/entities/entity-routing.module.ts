import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'data-tree-root',
        data: { pageTitle: 'dfmApp.dataTreeRoot.home.title' },
        loadChildren: () => import('./data-tree-root/data-tree-root.routes'),
      },
      {
        path: 'data-tree-branch',
        data: { pageTitle: 'dfmApp.dataTreeBranch.home.title' },
        loadChildren: () => import('./data-tree-branch/data-tree-branch.routes'),
      },
      {
        path: 'data-tree-leaf',
        data: { pageTitle: 'dfmApp.dataTreeLeaf.home.title' },
        loadChildren: () => import('./data-tree-leaf/data-tree-leaf.routes'),
      },
      {
        path: 'data-field',
        data: { pageTitle: 'dfmApp.dataField.home.title' },
        loadChildren: () => import('./data-field/data-field.routes'),
      },
      {
        path: 'data-tree-branch-to-field',
        data: { pageTitle: 'dfmApp.dataTreeBranchToField.home.title' },
        loadChildren: () => import('./data-tree-branch-to-field/data-tree-branch-to-field.routes'),
      },
      {
        path: 'data-tree-root-to-field',
        data: { pageTitle: 'dfmApp.dataTreeRootToField.home.title' },
        loadChildren: () => import('./data-tree-root-to-field/data-tree-root-to-field.routes'),
      },
      {
        path: 'data-tree-leaf-to-field',
        data: { pageTitle: 'dfmApp.dataTreeLeafToField.home.title' },
        loadChildren: () => import('./data-tree-leaf-to-field/data-tree-leaf-to-field.routes'),
      },
      {
        path: 'data-tree-branch-link',
        data: { pageTitle: 'dfmApp.dataTreeBranchLink.home.title' },
        loadChildren: () => import('./data-tree-branch-link/data-tree-branch-link.routes'),
      },
      {
        path: 'data-forest',
        data: { pageTitle: 'dfmApp.dataForest.home.title' },
        loadChildren: () => import('./data-forest/data-forest.routes'),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
