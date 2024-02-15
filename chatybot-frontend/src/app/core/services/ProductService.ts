
import { Product } from "../models/Product";
import { HttpClient} from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

@Injectable(
    {
        providedIn: 'root'
    }
)
export class ProductService{
    constructor(private http: HttpClient ) { }
    getEquipments(): Observable<Product> {
       return new Observable<any>((observer) => {
            const es = new EventSource('http://localhost:8080/api/v1/products');
            es.addEventListener('message', (evt:any) => {
                const parsedData = evt.data != null ? JSON.parse(evt.data) : evt.data;
                console.log('Received data:', parsedData);
                observer.next(parsedData);
            })
            es.addEventListener('error', (error) => {
                console.error('EventSource failed:', error);
                observer.error('EventSource failed');
              });
        
            es.addEventListener('complete', () => {
            console.log('EventSource stream complete');
            observer.complete();
            es.close(); 
            });
            return () => es.close();
        });

    }
    createProduct(product: Product): Observable<Product> {
        return this.http.post<Product>('http://localhost:8080/api/v1/products', product);
    }
}