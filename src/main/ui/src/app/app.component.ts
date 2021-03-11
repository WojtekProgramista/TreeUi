import { Component, OnInit } from '@angular/core';
import { NodeService } from './node.service';
import { Node } from './Node'
import { HttpErrorResponse } from '@angular/common/http';
import { NodeComponent } from './node/node.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  layeredNodes: Node[][]
  spaces: number[]
  maxId: number

  constructor(private nodeService: NodeService) {}

  public getSum(nodes: Node[], node: Node) {
    if (node.type == 'root')
      return node.value
    else if (node.type == 'intermediate') {
      for (let n of nodes) {
        if (n.id == node.parent)
          return node.value + this.getSum(nodes, n)
      }
    }
    else {
      for (let n of nodes) {
        if (n.id == node.parent)
          return this.getSum(nodes, n)
      }
    }
  }

  public getNodes(): void {
    this.nodeService.getNodes().subscribe(
      (response: Node[]) => {
        var currLayer: number = 0
        var nodesFetched: number = 0
        this.maxId = 0
        this.spaces = []
        this.layeredNodes = [[]]
        response.sort((a, b) => a.id.toString().localeCompare(b.id.toString()));

        for (let node of response) {
          if (node.type == 'root') {
            this.layeredNodes[currLayer].push(node)
            nodesFetched += 1
            currLayer += 1
          }

          if (node.id > this.maxId)
            this.maxId = node.id
        }

        for (let i = 0; i < this.maxId+1; i++)
          this.spaces.push(100)

        var children: Node[]

        while (nodesFetched < response.length) {
          this.layeredNodes.push([])

          for (let parentNode of this.layeredNodes[currLayer-1]) {
            children = []

            for (let childNode of response) {
              if (childNode.parent == parentNode.id)
                children.push(childNode)
            }

            if (children.length > 0) {
              for (let child of children) {
                this.spaces[child.id] = this.spaces[parentNode.id] / children.length
                if (child.type == 'leaf')
                   child.value = this.getSum(response, child)
                this.layeredNodes[currLayer].push(child)
                nodesFetched += 1
              }
            }
            else {
              this.maxId += 1
              this.layeredNodes[currLayer].push({
                id: this.maxId,
                value: null,
                parent: parentNode.id,
                color: 'white',
                type: 'empty'
              })
              this.spaces.push(this.spaces[parentNode.id])
            }
          }
          currLayer += 1
        }
      },
      (error: HttpErrorResponse) => {
        alert(error.message)
      }
    )
  }
  
  ngOnInit() {
    this.getNodes()
  }

}
