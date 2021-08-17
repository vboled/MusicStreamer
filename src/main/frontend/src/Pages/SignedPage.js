import {Button, List} from "antd";
import DrawPlaylist from "../Playlists/DrawPlaylist";
import {Link} from "react-router-dom";

const headerStyle = {
    backgroundColor: "#6d6161",
    height: 150
}

const greeting = {
    color: "#ffffff",
    fontSize: "64px"
}

const image = {
    marginTop: "-6%"
}

const secondDiv = {
    backgroundColor: "#d4cbcb",
    height: 1000
}

const SignedPage = (result) => {
    return (<div>
        <div style={headerStyle}>
        <Link to={`/user/${result.user.name}`}>View profile</Link>
        <h1 style={greeting}>Hello, {result.user.name}!!!</h1>
        <img style={image} src={window.location.origin + '/img/userDefault.png'} className={"circle"} width={100} align={"left"}/>
        </div>
        <div style={secondDiv}>
            <h1 style={{textAlign:"left", fontSize:"32px", paddingLeft:"10px"} }> Playlists:</h1>
            <h1>{"\n"}</h1>
            <List style={{alignItems:"left"}}>
              {result.playlistLists.map((playlist, index) =>
                  <li key={index}>{DrawPlaylist(playlist, index)}</li>
              )}
            </List>
        </div>
    </div>)
}

export default SignedPage;