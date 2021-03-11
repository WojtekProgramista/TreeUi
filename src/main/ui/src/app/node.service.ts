import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Node } from './Node';

@Injectable({
  providedIn: 'root'
})
export class NodeService {
  backendUrl = environment.backendUrl;

  constructor(private http: HttpClient) { }

  getNodes(): Observable<Node[]> {
    return this.http.get<Node[]>(`${this.backendUrl}/node/all`)
  }

  addNode(node: Node): Observable<Node> {
    return this.http.post<Node>(`${this.backendUrl}/node/add`, node)
  }

  updateNode(node: Node): Observable<Node> {
    return this.http.put<Node>(`${this.backendUrl}/node/update`, node)
  }

  deleteNode(id: number): Observable<void> {
    return this.http.delete<void>(`${this.backendUrl}/node/delete/${id}`)
  }

  makeLeaf(id: number): Observable<void> {
    return this.http.get<void>(`${this.backendUrl}/node/leaf/${id}`)
  }

  makeIntermediate(id: number): Observable<void> {
    return this.http.get<void>(`${this.backendUrl}/node/intermediate/${id}`)
  }
}
