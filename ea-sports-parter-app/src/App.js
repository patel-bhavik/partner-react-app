import {
  BrowserRouter as Router,
  Routes,
  Route,
} from 'react-router-dom'
import HomePage from './pages/HomePage';
import AboutPage from './pages/AboutPage';
import OfferCalendarPage from './pages/OfferCalendarPage';
import OfferPage from './pages/OfferPage';
import NavBar from './NavBar';
import NotFoundPage from './pages/NotFoundPage';
import './App.css';

function App() {
  return (
    <Router>
      <div className="App">
        <NavBar />
        <div id="page-body">
          <Routes>
            <Route path="/" element={ <HomePage /> } exact />
            <Route path="/about" element={ <AboutPage /> } />
            <Route path="/calendar" element={ <OfferCalendarPage /> } />
            <Route path="/offer/:title" element={ <OfferPage /> } />
            <Route path="*" element={ <NotFoundPage /> } />
          </Routes>
        </div>
      </div>
    </Router>
  );
}

export default App;
