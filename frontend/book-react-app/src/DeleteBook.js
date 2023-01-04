import React from "react";
import {TextField, Button} from "@material-ui/core";

class DeleteBook extends React.Component{
    constructor(props){
        super(props);
        this.state = {items: props.items, menu:3};
        this.delete=props.delete;

        //this.handleSubmit = this.handleSubmit.bind(this);
        //this.deleteEventHandler = this.deleteEventHandler.bind(this);
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

    // 제품 삭제
    deleteEventHandler = () => {
        var thisItem = this.state.items
        this.delete(thisItem);
        //console.log(this.state.items);
    }

    /*handleSubmit(event){
        event.preventDefault();  // submit 버튼 클릭 시 고유 동작을 막음

        // event가 발생한 폼의 데이터를 담는 객체
        // name속성과 value 값을 key:value 쌍으로 유지
        const data = new FormData(event.target);
        const title = data.get("title");

        console.log(this.state.items.filter(item => item.title === title));
        if(this.state.items.filter(item => item.title === title))
            this.delete(this.state.items);
        //this.delete(title);
    }*/

    render(){
        const item = this.state.items;
        return(
            <div>
                {/*<form noValidate onSubmit={this.handleSubmit}>*/}
                    <TextField
                        id={item.id}
                        name="title"
                        label="title"
                        value={item.title || ''}
                        variant="outlined"
                        size="small"
                        onChange={this.onInputChange}
                    />
                    <Button color='primary' variant='outlined'
                        onClick={this.deleteEventHandler}>제품 삭제</Button> &nbsp;
                {/*</form>*/}
            </div>
        )
    } 
}
export default DeleteBook;