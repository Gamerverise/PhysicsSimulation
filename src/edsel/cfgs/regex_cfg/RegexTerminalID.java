package edsel.cfgs.regex_cfg;

public enum RegexTerminalID {
    OP,         // open parenthesis
    CP,         // close parenthesis
    VB,         // vertical bar
    ST,         // star

    UB,         // under bar

    _0, _1, _2, _3, _4, _5, _6, _7, _8, _9,

    A, B ,C, E, F, G, H, I, J, K, L, M, N, O, P, Q, S, T, U, V, W, X, Y, Z,
    a, b ,c, e, f, g, h, i, j, k, l, m, n, o, p, q, s, t, u, v, w, x, y, z,

    LOP,        // literal open parenthesis
    LCP,        // literal close parenthesis

    LOBK,       // literal open bracket
    LCBK,       // literal close bracket

    LOBC,       // literal open bracket
    LCBC,       // literal close bracket

    LVB,        // literal vertical bar
    LST         // literal star
}
