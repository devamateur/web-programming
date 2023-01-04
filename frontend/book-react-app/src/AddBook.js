import React from 'react';
import {TextField, Button} from "@material-ui/core"

class AddBook extends React.Component{
    constructor(props){
        super(props);
        this.state = {item: {title:"", author:"", publisher:""}, menu:0};
        this.add=props.add;
        //this.update=props.update;
        //this.search=props.search;
        //this.delete=props.delete;
    }

    // onInputChange(): 텍스트필드 값이 변화할 때마다 문자열을 저장
    onInputChange = (e) => {
        const thisItem = this.state.item;

        if(e.target.name === "title")
            thisItem.title = e.target.value;
        if(e.target.name === "author")
            thisItem.author = e.target.value;
        if(e.target.name === "publisher")
            thisItem.publisher = e.target.value;

        this.setState({item: thisItem});
        
        //console.log(thisItem);
    }

    // onAddButtonClick(): 제품 추가 버튼 이벤트
    onAddButtonClick = () => {
        this.add(this.state.item);
        // 추가 후에는 빈 문자열로 초기화
        this.setState({item: {title: "", author: "", publisher: ""}});   
    }


    /*handleSubmit(event){
        event.preventDefault();  // submit 버튼 클릭 시 고유 동작을 막음

        // event가 발생한 폼의 데이터를 담는 객체
        // name속성과 value 값을 key:value 쌍으로 유지
        const data = new FormData(event.target);
        const title = data.get("title");
        const author = data.get("author");
        const publisher = data.get("publisher");

        var newItems = {id:"", title:title, author:author, publisher:publisher, userId:""};
        console.log(newItems);
        this.add({items: {title:title, author:author, publisher:publisher}});    
    }*/


    render(){
        const item=this.state.item;
        return(
            <form onSubmit={this.onAddButtonClick}>
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
                    type="submit">제품 추가</Button> &nbsp;
            </form>
        )
    }
}

export default AddBook;