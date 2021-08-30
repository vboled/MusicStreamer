import {Layout} from 'antd';

import "../App.css"
import 'antd/dist/antd.css';
import {useEffect, useRef} from "react";


const { Content} = Layout;

function HomePage(props) {

    const audioEl = useRef(null)

    // useEffect(() => {
    //     console.log("AA")
    //     audioEl.current.play()
    // }, [])

    return (<Content style={{ margin: '24px 16px 0' }}>
                <div className="site-layout-background" style={{ padding: 24, minHeight: "100vh" }}>
                    <h1>First</h1>
                    <audio controls={"controls"} src={'./audio/94831f90-37b1-4648-949d-1f5d3ddd2aa3.mp3'}></audio>
                    <h1>First</h1>
                </div>
            </Content>)

}

export default HomePage;