import React from 'react';
import AddBook from './AddBook';
import BookTable from './BookTable';
import SearchBook from './SearchBook';
import UpdateBook from './UpdateBook';
import DeleteBook from './DeleteBook';
import {Paper, Grid, Button, AppBar, Toolbar, Typography} from "@material-ui/core"
import './App.css';
import {call, signout} from "./service/ApiService";
//import Spinner from './assets/Spinner1.gif';

import Loading from './Loading';

class App extends React.Component{
  constructor(props){
    super(props);
    this.state = {
      items: {id:"", title:"", author:"", publisher:"", userId:""},
      // 로딩 중이라는 상태를 표현할 변수
      loading: true,
      menu:0
    }
  }

  // add(): 제품 추가 버튼을 누르면 아이템 추가
  /*add = (item) => {
    const thisItems = this.state.items;
    item.id = thisItems.length+"";  // key를 위한 id
    thisItems.push(item);
    this.setState({items: thisItems});
    console.log("Items: ", this.state.items);
  }*/

  // search()
  search = (item) => {
    const thisItems = this.state.items;

    //if(thisItems.title === item.title)
    const newItems = thisItems.filter(e => e.title === item.title)
    this.setState({items: newItems}, () => {
      console.log("Searched Items: ", this.state.items);
    })
  }

 /*delete = (item) => {
    const thisItems = this.state.items;
    // title값을 비교해서 해당 제목을 가진 아이템 삭제
    const newItems = thisItems.filter(e => e.title !== item.title)
    this.setState({items: newItems}, () => { 
      console.log("Update Items: ", this.state.items)
    });
  }*/

  
  componentDidMount(){
    call("/book", "GET", null).then((response) =>
      this.setState({items: response.data, loading: false})
    );
  }

  // 아이템 추가
  add = (item) => {
    call("/book", "POST", item).then((response) =>
      this.setState({items: response.data})
    );
  };

  delete = (item) => {
    call("/book", "DELETE", item).then((response) =>
      this.setState({items: response.data})
    );
  };

  update = (item) => {
    call("/book", "PUT", item).then((response) =>
      this.setState({items:response.data})
    );
  }

  changeMenu = (menuIndex) =>{
    this.setState({menu : menuIndex});
  }

  render(){
    const items = this.state.items;
    /*var bookTables = this.state.items.map((item, idx) => (
      <BookTable item={item} key={item.id} delete={this.delete}/>
    ));*/


    const menuList = {
      0: <AddBook add={this.add}/>,
      1: <SearchBook items={items} search={this.search}/>,
      2: <UpdateBook items={items} update={this.update}/>,
      3: <DeleteBook items={items} delete={this.delete} /> 
    };

    // 네비게이션 바
    var navigationBar = (
      <AppBar position="sticky">
        <Toolbar>
          <Grid justifyContent="space-between" container>
            <Grid item>
              <Typography variant="h6">도서 테이블</Typography>
            </Grid>
            <Grid>
            <Button color="inherit" onClick={() => this.changeMenu(0)}>제품 추가</Button>
              <Button color="inherit" onClick={() => this.changeMenu(1)}>제품 검색</Button>
              <Button color="inherit" onClick={() => this.changeMenu(2)}>제품 수정</Button>
              <Button color="inherit"onClick={() => this.changeMenu(3)}>제품 삭제</Button>
            </Grid>
            <Grid>
              <Button color="inherit" onClick={signout}>
                로그아웃
              </Button>
            </Grid>
          </Grid>
        </Toolbar>
      </AppBar>
    )

    // 로딩이 끝난 경우
    var bookListPage = (
      <div>
        {navigationBar}
        <Paper style={{width:matchMedia, height:matchMedia, margin:20}} elevation={3}>
            {/*<AddBook items={items}
                  add={this.add} update={this.update} delete={this.delete}></AddBook>*/}
            {menuList[this.state.menu]}
            {/*bookTables*/}
            {<BookTable items={items} delete={this.delete}/>}

        </Paper>
      </div>
    )


    return(
        <div className="App">
          {this.state.loading ? <Loading /> : bookListPage}
        </div>
    );
  }
}

export default App;
