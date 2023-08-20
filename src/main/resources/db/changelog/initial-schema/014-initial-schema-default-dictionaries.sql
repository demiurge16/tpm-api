INSERT INTO accuracy (id, name, description, active) VALUES
(uuid_generate_v4(), 'Exact', 'Exact accuracy level', true),
(uuid_generate_v4(), 'Estimated', 'Estimated accuracy level', true),
(uuid_generate_v4(), 'Unspecified', 'Unspecified accuracy level', true);

INSERT INTO industry (id, name, description, active) VALUES
(uuid_generate_v4(), 'Business/Administration', 'Business and Administration industry', true),
(uuid_generate_v4(), 'Tech/Engineering', 'Tech and Engineering industry', true),
(uuid_generate_v4(), 'Pharmacy/Medicine', 'Pharmacy and Medicine industry', true),
(uuid_generate_v4(), 'Science', 'Science industry', true),
(uuid_generate_v4(), 'Marketing', 'Marketing industry', true),
(uuid_generate_v4(), 'Art/Literary', 'Art and Literary industry', true),
(uuid_generate_v4(), 'Law', 'Law industry', true),
(uuid_generate_v4(), 'Patents', 'Patents industry', true),
(uuid_generate_v4(), 'Finance/Insurance', 'Finance and Insurance industry', true),
(uuid_generate_v4(), 'Social sciences', 'Social sciences industry', true),
(uuid_generate_v4(), 'History', 'History industry', true),
(uuid_generate_v4(), 'Entertainment/Media', 'Entertainment and Media industry', true),
(uuid_generate_v4(), 'E-commerce', 'E-commerce industry', true),
(uuid_generate_v4(), 'IT/Telecommunication', 'IT and Telecommunication industry', true);

INSERT INTO service_type (id, name, description, active) VALUES
(uuid_generate_v4(), 'Translation', 'Translation service', true),
(uuid_generate_v4(), 'Interpreting', 'Interpreting service', true),
(uuid_generate_v4(), 'Proofreading', 'Proofreading service', true),
(uuid_generate_v4(), 'Revision', 'Revision service', true),
(uuid_generate_v4(), 'OCR', 'OCR service', true),
(uuid_generate_v4(), 'DTP', 'DTP service', true),
(uuid_generate_v4(), 'Delivery', 'Delivery service', true),
(uuid_generate_v4(), 'AV translation', 'AV translation service', true),
(uuid_generate_v4(), 'Transcreation', 'Transcreation service', true),
(uuid_generate_v4(), 'Subtitles', 'Subtitles service', true),
(uuid_generate_v4(), 'Copywriting', 'Copywriting service', true),
(uuid_generate_v4(), 'Transcription', 'Transcription service', true),
(uuid_generate_v4(), 'Sworn Translation', 'Sworn Translation service', true),
(uuid_generate_v4(), 'Certification', 'Certification service', true),
(uuid_generate_v4(), 'TEP', 'TEP service', true),
(uuid_generate_v4(), 'TE', 'TE service', true),
(uuid_generate_v4(), 'NS proofreading', 'NS proofreading service', true),
(uuid_generate_v4(), 'TP', 'TP service', true),
(uuid_generate_v4(), 'Good Choice', 'Good Choice service', true),
(uuid_generate_v4(), 'Smart Choice', 'Smart Choice service', true),
(uuid_generate_v4(), 'Smart Choice+', 'Smart Choice+ service', true),
(uuid_generate_v4(), 'Perfect Choice', 'Perfect Choice service', true),
(uuid_generate_v4(), 'Top Choice', 'Top Choice service', true),
(uuid_generate_v4(), 'Unique Choice', 'Unique Choice service', true),
(uuid_generate_v4(), 'Post-editing', 'Post-editing service', true),
(uuid_generate_v4(), 'Machine translation', 'Machine translation service', true),
(uuid_generate_v4(), 'LQA', 'LQA service', true),
(uuid_generate_v4(), 'IP Services', 'IP Services', true);

INSERT INTO expense_category (id, name, description, active) VALUES
(uuid_generate_v4(), 'PM Job', 'PM Job expense category', true),
(uuid_generate_v4(), 'Delivery', 'Delivery expense category', true),
(uuid_generate_v4(), 'Software & Tools', 'Software & Tools expense category', true),
(uuid_generate_v4(), 'Travel & Accomodation', 'Travel & Accomodation expense category', true),
(uuid_generate_v4(), 'John Doe', 'John Doe expense category', true),
(uuid_generate_v4(), 'Other', 'Other expense category', true);

INSERT INTO unit (id, name, description, volume, measurement, active) VALUES
(uuid_generate_v4(), 'Minute', 'Minute unit', 1, 'POINTS', true),
(uuid_generate_v4(), 'Act (10 minutes)', 'Act (10 minutes) unit', 2, 'POINTS', true),
(uuid_generate_v4(), 'Hour', 'Hour unit', 1, 'HOURS', true),
(uuid_generate_v4(), 'Page (1,800 chars, incl. spaces)', 'Page (1,800 chars, incl. spaces) unit', 1800, 'CHARACTERS', true),
(uuid_generate_v4(), 'Page (1500 chars incl. spaces)', 'Page (1500 chars incl. spaces) unit', 1500, 'CHARACTERS', true),
(uuid_generate_v4(), 'Certified page (1,125 chars, incl. spaces)', 'Certified page (1,125 chars, incl. spaces) unit', 1125, 'CHARACTERS', true),
(uuid_generate_v4(), 'Sheet (200,000 chars, incl. spaces)', 'Sheet (200,000 chars, incl. spaces) unit', 200000, 'CHARACTERS', true),
(uuid_generate_v4(), 'Verse (60 chars, incl. spaces)', 'Verse (60 chars, incl. spaces) unit', 60, 'CHARACTERS', true),
(uuid_generate_v4(), 'Word (6 chars, incl. spaces)', 'Word (6 chars, incl. spaces) unit', 6, 'CHARACTERS', true),
(uuid_generate_v4(), 'Character', 'Character unit', 1, 'CHARACTERS', true);

INSERT INTO priority (id, name, description, emoji, value, active) VALUES
(uuid_generate_v4(), 'Unspecified', 'Unspecified priority', '❓', 0, true),
(uuid_generate_v4(), 'Freeze', 'Freeze priority', '❄️', 10, true),
(uuid_generate_v4(), 'Low', 'Low priority', '⬇️', 20, true),
(uuid_generate_v4(), 'Normal', 'Normal priority', '👍', 100, true),
(uuid_generate_v4(), 'High', 'High priority', '⬆️', 500, true),
(uuid_generate_v4(), 'Urgent', 'Urgent priority', '🔥', 1000, true);

INSERT INTO client_type (id, name, description, corporate, active) VALUES
(uuid_generate_v4(), 'Domestic individual', 'Domestic individual client type', false, true),
(uuid_generate_v4(), 'Foreign individual', 'Foreign individual client type', false, true),
(uuid_generate_v4(), 'Foreign corporate', 'Foreign corporate client type', true, true),
(uuid_generate_v4(), 'Domestic corporate', 'Domestic corporate client type', true, true),
(uuid_generate_v4(), 'Domestic institution', 'Domestic institution client type', true, true),
(uuid_generate_v4(), 'Foreign institution', 'Foreign institution client type', true, true);
