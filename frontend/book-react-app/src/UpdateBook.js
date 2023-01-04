import React from "react";
import {TextField, Button} from "@material-ui/core";

class UpdateBook extends React.Component{
    constructor(props){
        super(props);
        this.state = {items: {title:"", author:"", publisher:"", userId:""}, menu:2};
        this.update=props.updateEventHandler;
    }

    // onInputChange(): 텍스트필드 값이 변화할 때마다 문자열을 저장
    onInputChange = (e) => {
        const thisItem = this.state.items;
    
        if(e.target.name === "title")
            thisItem.title = e.target.value;
        if(e.target.name === "author")
            thisItem.author = e.target.value;
        if(e.target.name === "publisher")
            thisItem.publisher = e.target.value;
    
        this.setState({items: thisItem});
            
            //console.log(thisItem);
    }

    // 제품 수정
    updateEventHandler = (e) => {
        var thisItems = this.state.items;
        console.log(thisItems.title + "vs" + e.target.value.title);
        if(thisItems.title === e.target.title.value)
            this.update(this.state.items);
    }

    render(){
        const item=this.state.items;

        return(
            <div>
                <div>
                    <TextField
                        id={item.id}
                        name="title"
                        label="title"
                        value={item.title || ''}
                        variant="outlined"
                        size="small"
                        onChange={this.onInputChange}
                    />
                </div>
                <br></br>
                    
                <div>
                    <TextField
                        id={item.id}
                        name="author"
                        label="author"
                        value={item.author || ''}
                        variant="outlined"
                        size="small"
                        onChange={this.onInputChange}
                    />
                </div>
                <br></br>
                    
                <div>
                    <TextField
                        id={item.id}
                        name="publisher"
                        label="publisher"
                        value={item.publisher || ''}
                        variant="outlined"
                        size="small"
                        onChange={this.onInputChange}
                    />
                </div>

                <br></br>
                <Button color='primary' variant='outlined'
                        onClick={this.update}>제품 수정</Button> &nbsp;
            </div>
        )
    } 
}

export default UpdateBook;