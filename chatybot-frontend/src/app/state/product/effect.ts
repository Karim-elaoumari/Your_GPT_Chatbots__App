import { Injectable } from "@angular/core";
import { Actions, createEffect, ofType } from "@ngrx/effects";
import { ProductService } from "src/app/core/services/ProductService";
import { LoadProductsFailure, LoadProductsItemSuccess, LoadProductsSuccess, ProductActionTypes } from "./action";
import { Observable, catchError, map, of, switchMap } from "rxjs";
import { Product } from "src/app/core/models/Product";
import { Action } from "@ngrx/store";

@Injectable()
export class ProductEffects {
    constructor(
        public productService: ProductService,
        public effectActions$: Actions
    ) { }
    loadProducts: Observable<Action> =createEffect(() =>
             
            this.effectActions$.pipe(
                ofType(ProductActionTypes.LOAD_PRODUCTS),
                switchMap(() =>{
                    return this.productService.getEquipments().pipe(
                        map((product: Product)=> new LoadProductsItemSuccess(product)),
                        catchError(err => of(new LoadProductsFailure(err)))
                    )
                     }
                )
            )
    );
    createProduct: Observable<Action> =createEffect(() =>

        this.effectActions$.pipe(
            ofType(ProductActionTypes.CREATE_PRODUCT),
            switchMap((action:any) =>{
                return this.productService.createProduct(action.payload).pipe(
                    map((product: Product)=> new LoadProductsItemSuccess(product)),
                    catchError(err => of(new LoadProductsFailure(err)))
                )
                 }
            )
        )
);

}