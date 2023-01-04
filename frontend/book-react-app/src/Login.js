import React from 'react';
import {signin} from "./service/ApiService";
import {Link, Button, TextField, Grid, Typography, Container} 
    from  "@material-ui/core";

class Login extends React.Component{
    constructor(props){
        super(props);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleSubmit(event){
        event.preventDefault();  // submit 버튼 클릭 시 고유 동작을 막음

        // event가 발생한 폼의 데이터를 담는 객체
        // name속성과 value 값을 key:value 쌍으로 유지
        const data = new FormData(event.target);
        const email = data.get("email");

        const password = data.get("password");
        // ApiService의 signin 메서들 사용해 로그인
        signin({email: email, password: password});
    }

    render(){
        return (
            <Container component="main" maxWidth="xs" style={{marginTop: "8%"}}>
                <Grid container spacing={2}>
                    <Grid item xs={12}>
                        <Typography component="h1" variant="h5">
                            로그인
                        </Typography>
                    </Grid>
                </Grid>
                <form noValidate onSubmit={this.handleSubmit}>
                    {" "}
                    <Grid container spacing={2}>
                        <Grid item xs={12}>
                            <TextField
                                variant="outlined"
                                required
                                fullWidth
                                id="email"
                                label="이메일"
                                name="email"
                                autoComplete="email"
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                variant="outlined"
                                required
                                fullWidth
                                name="password"
                                label="비밀번호"
                                type="password"
                                id="password"
                                autoComplete="current-password"
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <Button
                                type="submit"
                                fullWidth
                                variant="contained"
                                color="primary"
                            >
                                로그인
                            </Button>
                        </Grid>
                        <Link href="/signup" variant="body2">
                            <Grid item>계정이 없다면 클릭하여 가입하세요.</Grid>
                        </Link>
                    </Grid>
                </form>
            </Container>
        )
    }
}
export default Login;