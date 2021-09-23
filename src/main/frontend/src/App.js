import { Route, BrowserRouter as Router, Switch } from 'react-router-dom';
import './App.css';
import Start from './Start';
import GameSession from './GameSession';
import GameOver from './GameOver';
import Home from "./Home";
import Leaderboard from "./Leaderboard";

function App() {
  return (
    <Router>
      <div className="App">
        <Switch>
          <Route exact path="/">
            <Home />
          </Route>
          <Route exact path="/start">
            <Start />
          </Route>
          <Route exact path="/game/:name/:sessionId/:maxTries">
            <GameSession />
          </Route>
          <Route exact path="/gameover/:name/:condition/:answer">
            <GameOver />
          </Route>
          <Route exact path="/leaderboard">
            <Leaderboard />
          </Route>
        </Switch>
      </div>
    </Router>
  );
}

export default App;
