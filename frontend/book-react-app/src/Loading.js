import React from 'react';
import Spinner from './assets/Spinner1.gif';
import {Container} from "@material-ui/core";

export default () => {
    return (
        <Container>
            <h3>잠시만 기다려 주세요</h3>
            <img src={Spinner} alt="로딩중" width="5%"/>
        </Container>
    );
};