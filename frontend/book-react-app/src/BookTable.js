import React from 'react';

import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, Button } 
from "@material-ui/core";

class BookTable extends React.Component{
    constructor(props){
        super(props);
        this.state={items: props.items};
        //this.add=props.add;
        //this.update=props.update;
        this.delete=props.delete;
    }

    deleteTableHandler = () => {
        this.delete(this.state.items);
    }

    render(){
        const bookItems = this.state.items;
        return(
            <TableContainer component={Paper}>
                <Table size="small">
                    <TableHead>
                        <TableRow>
                            <TableCell align="right">Id</TableCell>
                            <TableCell align="right">Title</TableCell>
                            <TableCell align="right">Author</TableCell>
                            <TableCell align="right">Publisher</TableCell>
                            <TableCell align="right">UserId</TableCell>
                            <TableCell align="center">삭제</TableCell>
                        </TableRow>
                    </TableHead>

                    <TableBody>
                        {bookItems.map(({ id, title, author, publisher, userId }) => (
                            <TableRow key={id}>
                                <TableCell align="right">{id}</TableCell>
                                <TableCell align="right">{title}</TableCell>
                                <TableCell align="right">{author}</TableCell>
                                <TableCell align="right">{publisher}</TableCell>
                                <TableCell align="right">{userId}</TableCell>
                                <TableCell align="center">
                                    <Button color='primary' variant='outlined'
                                        type="button" onClick={this.deleteTableHandler}>delete</Button>
                                </TableCell>
                            </TableRow>
                        ))}
                        </TableBody>
                </Table>             
            </TableContainer>
        )
    }
}

export default BookTable;