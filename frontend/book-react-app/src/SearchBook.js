import React from "react";
import {TextField, Button} from "@material-ui/core";

/*function Search({onSubmit}) {
    const handleSubmit = (event) => {
        event.preventDefault();
        onSubmit(event.target.elements.filter.value);
    };
}*/

class SearchBook extends React.Component{
    constructor(props){
        super(props);
        this.state = {items: [props.items], menu:1};
        this.search=props.search;
        
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

    // 제품 검색 버튼을 누르면 textfield에 데이터 나타내기
    searchEventHandler = () => {
        const thisItems = this.state.items;   // 현재 데이터
        this.search(thisItems);
    }

    render(){
        const item=this.state.items;

        return(
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
                <Button color='primary' variant='outlined'
                        onClick={this.searchEventHandler}>제품 검색</Button> &nbsp;
            </div>
        )
    } 
}

/*function SearchBook({onSubmit}) {
    const handleSubmit = (event) => {
        event.preventDefault();
        onSubmit(event.target.elements.filter.value);
    };

    return(
        <form onSubmit={handleSubmit}>
            <TextField
                //id={item.id}
                name="title"
                label="title"
                //value={item.title || ''}
                variant="outlined"
                size="small"
            />
        </form>
    )
}*/



export default SearchBook;