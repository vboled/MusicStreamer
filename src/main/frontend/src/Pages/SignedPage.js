import {Button, List} from "antd";
import DrawPlaylist from "../Playlists/DrawPlaylist";
import {Link} from "react-router-dom";
import "../App.css"

const headerStyle = {
    backgroundColor: "#6d6161",
    height: 150
}

const greeting = {
    color: "#ffffff",
    fontSize: "32px"
}

const image = {
    marginTop: "0%",
    float:"left"
}

const secondDiv = {
    backgroundColor: "#d4cbcb",
    height: 1000
}


const SignedPage = (result) => {
    return (<div>
        <nav>
            <img style={image} src={window.location.origin + '/img/userDefault.png'} className={"circle"} width={100} align={"left"}/>
            <ul className="nav-links">
                <Link to={`/user/${result.user.name}`}>
                    <li>View profile</li>
                </Link>
                <Link>
                    <li>Playlist</li>
                </Link>
                <Link>
                    <li>Artists</li>
                </Link>
                <li></li>
            </ul>
        </nav>
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