import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {UserDashboardComponent} from './user-dashboard.component';
import {RouterLink, RouterOutlet} from "@angular/router";
import {FooterComponent} from './footer/footer.component';
import {HeaderComponent} from './header/header.component';
import {SidebarComponent} from './sidebar/sidebar.component';
import {DashboardComponent} from './dashboard/dashboard.component';
import {ProductsComponent} from './products/products.component';
import {UsersComponent} from './users/users.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {EditUserComponent} from './users/edit-user/edit-user.component';
import {AllUsersComponent} from './users/all-users/all-users.component';
import {AddNewUserComponent} from './users/add-new-user/add-new-user.component';
import {SuppliersComponent} from './suppliers/suppliers.component';
import {AllSuppliersComponent} from './suppliers/all-suppliers/all-suppliers.component';
import {AddNewSupplerComponent} from './suppliers/add-new-suppler/add-new-suppler.component';
import {EditSupplierComponent} from './suppliers/edit-supplier/edit-supplier.component';
import {CompaniesComponent} from './suppliers/companies/companies.component';
import {AddNewCompanyComponent} from './suppliers/companies/add-new-company/add-new-company.component';
import {EditCompanyComponent} from './suppliers/companies/edit-company/edit-company.component';
import {AllProductsComponent} from './products/all-products/all-products.component';
import {AddNewProductComponent} from './products/add-new-product/add-new-product.component';
import {EditProductComponent} from './products/edit-product/edit-product.component';
import {BrandsComponent} from './brands/brands.component';
import {AllBrandsComponent} from './brands/all-brands/all-brands.component';
import {AddBrandComponent} from './brands/add-brand/add-brand.component';
import {EditBrandComponent} from './brands/edit-brand/edit-brand.component';
import {CategoriesComponent} from './categories/categories.component';
import {AllCategoriesComponent} from './categories/all-categories/all-categories.component';
import {AddCategoryComponent} from './categories/add-category/add-category.component';
import {EditCategoryComponent} from './categories/edit-category/edit-category.component';
import {GrnComponent} from './grn/grn.component';
import {AddGrnComponent} from './grn/add-grn/add-grn.component';
import {SupplierModalComponent} from './modals/supplier-modal/supplier-modal.component';
import {ProductsModalComponent} from './modals/products-modal/products-modal.component';
import {GrnHistoryComponent} from './grn/grn-history/grn-history.component';
import {StockComponent} from './stock/stock.component';
import {UpdateStockModalComponent} from './stock/update-stock-modal/update-stock-modal.component';
import {InvoiceComponent} from './invoice/invoice.component';
import {AddInvoiceComponent} from './invoice/add-invoice/add-invoice.component';
import {StockModalComponent} from './modals/stock-modal/stock-modal.component';
import {InvoiceHistoryComponent} from './invoice/invoice-history/invoice-history.component';

@NgModule({
    declarations: [
        UserDashboardComponent,
        FooterComponent,
        HeaderComponent,
        SidebarComponent,
        DashboardComponent,
        ProductsComponent,
        UsersComponent,
        EditUserComponent,
        AllUsersComponent,
        AddNewUserComponent,
        SuppliersComponent,
        AllSuppliersComponent,
        AddNewSupplerComponent,
        EditSupplierComponent,
        CompaniesComponent,
        AddNewCompanyComponent,
        EditCompanyComponent,
        AllProductsComponent,
        AddNewProductComponent,
        EditProductComponent,
        BrandsComponent,
        AllBrandsComponent,
        AddBrandComponent,
        EditBrandComponent,
        CategoriesComponent,
        AllCategoriesComponent,
        AddCategoryComponent,
        EditCategoryComponent,
        GrnComponent,
        AddGrnComponent,
        SupplierModalComponent,
        ProductsModalComponent,
        GrnHistoryComponent,
        StockComponent,
        UpdateStockModalComponent,
        InvoiceComponent,
        AddInvoiceComponent,
        StockModalComponent,
        InvoiceHistoryComponent
    ],
    imports: [
        CommonModule,
        RouterOutlet,
        RouterLink,
        FormsModule,
        ReactiveFormsModule
    ]
})
export class UserDashboardModule {
}
