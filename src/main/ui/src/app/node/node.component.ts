import { Component, Input, OnInit } from '@angular/core';
import { Node } from '../Node';
import { NodeService } from '../node.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-node',
  templateUrl: './node.component.html',
  styleUrls: ['./node.component.css']
})
export class NodeComponent implements OnInit{

  @Input('node') node: Node
  @Input('space') space: number
  @Input('maxId') maxId: number
  fromDisplay: string
  action: string
  
  constructor(private nodeService: NodeService) {}

  ngOnInit() {
    this.fromDisplay = 'none'
    this.action = null
  }

  displayForm(action: string) {
    this.fromDisplay = 'block'
    this.action = action
  }

  refresh() {
    window.location.reload();
  }

  onSubmit = function(data) {
    if (this.action == 'add') {
      this.nodeService.addNode({
        id: this.maxId + 1,
        value: null,
        parent: this.node.id,
        color: data.color,
        type: 'leaf'
      }).subscribe(
          (response: Node) => {
            if (this.node.parent != null)
              this.nodeService.updateNode({
                id: this.node.id,
                value: this.node.value,
                parent: this.node.parent,
                color: this.node.color,
                type: 'intermediate'
              }).subscribe(
                  (response: void) => this.refresh()
                )
              else
                this.refresh()
          }
        )
    }
    else if (this.action == 'edit') {
      this.nodeService.updateNode({
        id: this.node.id,
        value: data.value,
        parent: this.node.parent,
        color: data.color,
        type: this.node.type
      }).subscribe(
          (response: Node) => this.refresh()
        )
    }
  }

  deleteNode() {
    this.nodeService.deleteNode(this.node.id).subscribe(
      (response: void) => {
        if (this.node.parent != null)
          this.nodeService.makeLeaf(this.node.parent).subscribe(
              (response: void) => this.refresh()
            )
          else
            this.refresh()
      }
  )
  }

}

